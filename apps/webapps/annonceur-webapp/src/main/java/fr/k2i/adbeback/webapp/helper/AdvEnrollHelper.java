package fr.k2i.adbeback.webapp.helper;

import com.google.common.collect.Sets;
import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.CustomerTarget;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import fr.k2i.adbeback.webapp.bean.enroll.*;
import fr.k2i.adbeback.webapp.bean.enroll.adv.AdvEnrollCommand;
import fr.k2i.adbeback.webapp.bean.enroll.adv.AdvUserBean;
import fr.k2i.adbeback.webapp.bean.enroll.adv.CustomerTargetCommand;
import fr.k2i.adbeback.webapp.bean.enroll.adv.CustomizeCommand;
import fr.k2i.adbeback.webapp.bean.enroll.agency.*;
import fr.k2i.adbeback.webapp.controller.UploadController;
import fr.k2i.adbeback.webapp.facade.FileUtils;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.state.enroll.AdvEnrollFlowState;
import fr.k2i.adbeback.webapp.state.enroll.AdvEnrollFlowState;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 16:27
 * Goal:
 */
@Service
public class AdvEnrollHelper {


    private static Logger logger = LogHelper.getLogger(AdvEnrollHelper.class);

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private ICityDao cityDao;

    @Value("${addonf.logo.location}")
    private String logoPath;

    @Value("${addonf.logo.private}")
    private String privatePath;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleDao roleDao;


    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IMailEngine mailEngine;

    @Value("${adv.base.user.confirm.url}")
    private String advUserConfirmBaseUrl;


    @Autowired
    private MessageSource messageSource;


    @Autowired
    private IBrandDao brandDao;

    @Resource(name = "annonceurUserDao")
    private IWebUserDao userDao;


    @Autowired
    private ISectorDao sectorDao;


    public List<String> availableFiles(AdvEnrollFlowState state){
        return state.getRegistration().availableFiles();
    }

    public List<String> requiredFiles(AdvEnrollFlowState state){
        return state.getRegistration().requiredFiles();
    }

    public Map<String,String> uploadedFileStatus(RequestContext context,AdvEnrollFlowState state){

        HttpSession session = ((HttpServletRequest)context.getExternalContext().getNativeRequest()).getSession();
        Map<String,FileCommand> uploaded = (Map<String, FileCommand>) session.getAttribute(UploadController.UPLOADED_PARAM);
        if(session.getAttribute(UploadController.UPLOADED_PARAM)==null){
            uploaded = new HashMap<String, FileCommand>();
            session.setAttribute(UploadController.UPLOADED_PARAM,uploaded);
        }

        Map<String,String> res = new HashMap<String, String>();

        List<String> filesNeeded = state.getRegistration().requiredFiles();
        for (String file : filesNeeded) {
            FileCommand fileCommand = uploaded.get(file);
            if(fileCommand!=null){
                res.put(file, fileCommand.getFileName());
            }else{
                res.put(file, "");
            }
        }

        return res;
    }


    public String saveFiles(RequestContext context,AdvEnrollCommand advEnrollCommand,AdvEnrollFlowState state){
        HttpSession session = ((HttpServletRequest)context.getExternalContext().getNativeRequest()).getSession();
        Map<String,FileCommand> uploaded = (Map<String, FileCommand>) session.getAttribute(UploadController.UPLOADED_PARAM);

        boolean needMoreFile = false;

        AttachementsCommand attachements = advEnrollCommand.getAttachements();
        Map<String, FileCommand> files = attachements.getFiles();
        if (files == null){
            files = new HashMap<String, FileCommand>();
            attachements.setFiles(files);
        }

        List<String> availables = state.getRegistration().availableFiles();
        List<String> required = state.getRegistration().requiredFiles();

        for (String available : availables) {
            FileCommand fileCommand = uploaded.get(available);
            files.put(available,fileCommand);
            if(required.contains(available) && fileCommand ==null){
                needMoreFile = true;
            }
        }

        return needMoreFile?"needMoreFile":"ok";
    }


    @Transactional
    public void createAccount(RequestContext context,AdvEnrollCommand advEnrollCommand,AdvEnrollFlowState state) throws ParseException, IOException {

        Brand brand = new Brand();

        InformationCommand info = advEnrollCommand.getInfo();
        AddressBean addressBean = info.getAddress();

        Address address = new Address();
        address.setCity(cityDao.findByZipcodeAndCityAndCountry_Code(addressBean.getZipcode(), addressBean.getCity(), addressBean.getCountry()));
        address.setCountry(countryDao.findByCode(addressBean.getCountry()));
        address.setAddress(addressBean.getStreet());

        brand.setAddress(address);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        brand.setCreatedDate(sdf.parse(info.getCreationDate()));

        brand.setLegalStatus(info.getLegalStatus());
        brand.setName(info.getName());
        brand.setPhone(info.getPhone());
        brand.setSiret(info.getSiret());


        Map<String, Attachement> fileUploaded = new HashMap<String, Attachement>();
        AttachementsCommand attachements = advEnrollCommand.getAttachements();
        Map<String, FileCommand> files = attachements.getFiles();
        for (Map.Entry<String, FileCommand> file : files.entrySet()) {
            String key = file.getKey();
            FileCommand value = file.getValue();

            if("LOGO".equals(key)){
                brand.setLogo(FileUtils.saveFile(value.getContent(), logoPath));
            }else{
                Attachement attachement = new Attachement();
                attachement.setStatus(AttachementStatus.PRESENT);
                String originalName = value.getFileName();
                attachement.setOriginalName(originalName);
                attachement.setSize(value.getSize());

                attachement.setFullPath(FileUtils.saveFile(value.getContent(), privatePath));

                int dot = originalName.lastIndexOf(".");
                attachement.setExtention(originalName.substring(dot + 1));

                fileUploaded.put(key,attachement);

            }



        }

        brand.setAttachements(fileUploaded);

        CustomizeCommand customize = advEnrollCommand.getCustomize();
        List<CustomerTargetCommand> customersTarget = customize.getCustomersTarget();

        List<CustomerTarget> tcs = new ArrayList<CustomerTarget>();
        for (CustomerTargetCommand customerTargetCommand : customersTarget) {
            CustomerTarget target = new CustomerTarget(customerTargetCommand.getSex(),customerTargetCommand.getAgeGroup());
            tcs.add(target);
        }
        brand.setTargetCustomers(tcs);

        Long sectorId = customize.getSectorId();
        brand.setSector(sectorDao.get(sectorId));

        List<MediaType> targetMedia = customize.getTargetMedia();
        brand.setTargetMedia(targetMedia);

        brand = brandDao.save(brand);

        AdvUserBean advUserBean = advEnrollCommand.getUser();

            BrandUser user = new BrandUser();
            user.setAddress(null);

            user.setAccountExpired(false);
            user.setAccountLocked(false);
            user.setCredentialsExpired(false);
            user.setEnabled(false);

            user.setEmail(advUserBean.getEmail());
            user.setPassword(passwordEncoder.encodePassword(advUserBean.getPassword(), null));
            user.setPhone(advUserBean.getPhone());

            user.setUsername(advUserBean.getEmail());
            user.setFirstname(advUserBean.getFirstname());
            user.setLastname(advUserBean.getLastname());

            user.setRoles(Sets.<Role>newHashSet(roleDao.getRoleByName(Constants.ANNONCEUR_ROLE)));

            user = (BrandUser) userDao.save(user);
            brand.setUser(user);



            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getNativeRequest();
            Locale locale = request.getLocale();


            String url = advUserConfirmBaseUrl + desCryptoService.generateOtpConfirm(brand.getName() + "|" + user.getEmail(), user, 48);

            Map<String, Object> model = new HashMap<String, Object>();

            model.put("url", url);
            model.put("user", user);
            model.put("brand", brand);

            Email email = Email.builder()
                    .subject(messageSource.getMessage("mail.enrolled.brand", new Object[]{}, locale))
                    .model(model)
                    .content("email/adv_user_enrolled")
                    .recipients(user.getEmail())
                    .noAttachements()
                    .build();
            try {
                mailEngine.sendMessage(email, locale);
            } catch (SendException e) {
                logger.error("error sending email", e);
            }

        try {
            User currentUser = userFacade.getCurrentUser();
            if (currentUser instanceof AgencyUser) {
                AgencyUser u = (AgencyUser) currentUser;
                u.addInChargeOf(brand);
            }
        } catch (Exception e) {
            logger.debug("not loggued user");
        }

        brandDao.save(brand);


    }


    public String addTarget(AdvEnrollCommand advEnrollCommand){
        CustomerTargetCommand currentCustomerTarget = advEnrollCommand.getCustomize().getCurrentCustomerTarget();
        currentCustomerTarget.setUid(UUID.randomUUID().toString());
        advEnrollCommand.getCustomize().getCustomersTarget().add(currentCustomerTarget);
        advEnrollCommand.getCustomize().setCurrentCustomerTarget(new CustomerTargetCommand());
        return "ok";
    }

    public String isCustomerTargetEmpty(AdvEnrollCommand advEnrollCommand){
        List<CustomerTargetCommand> customersTarget = advEnrollCommand.getCustomize().getCustomersTarget();
        return customersTarget.isEmpty()?"empty":"ok";
    }


    public void deleteTarget(AdvEnrollCommand advEnrollCommand,String uid){
        List<CustomerTargetCommand> customersTarget = advEnrollCommand.getCustomize().getCustomersTarget();
        CustomerTargetCommand toDelete = null;
        for (CustomerTargetCommand customerTargetCommand : customersTarget) {
           if(customerTargetCommand.getUid().equals(uid)){
               toDelete = customerTargetCommand;
           }
        }

        customersTarget.remove(toDelete);
    }
}
