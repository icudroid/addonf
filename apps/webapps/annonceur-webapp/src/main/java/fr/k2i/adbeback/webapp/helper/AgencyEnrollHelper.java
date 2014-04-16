package fr.k2i.adbeback.webapp.helper;

import com.google.common.collect.Sets;
import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import fr.k2i.adbeback.webapp.bean.enroll.*;
import fr.k2i.adbeback.webapp.bean.enroll.agency.*;
import fr.k2i.adbeback.webapp.controller.UploadController;
import fr.k2i.adbeback.webapp.facade.FileUtils;
import fr.k2i.adbeback.webapp.state.enroll.AgencyEnrollFlowState;
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
public class AgencyEnrollHelper {

    private static Logger logger = LogHelper.getLogger(AgencyEnrollHelper.class);

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

    @Value("${agency.base.admin.confirm.url}")
    private String agencyAdminConfirmBaseUrl;

    @Value("${agency.base.user.confirm.url}")
    private String agencyUserConfirmBaseUrl;


    @Autowired
    private MessageSource messageSource;


    @Autowired
    private IAgencyDao agencyDao;

    @Resource(name = "annonceurUserDao")
    private IWebUserDao userDao;

    public void createAdmin(AgencyEnrollCommand agencyEnrollCommand){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();

        //find if admin exist
        AgencyUserBean currentAdmin = null;
        for (AgencyUserBean agencyUserBean : users.getUsers()) {
            if(AgencyRole.ADMIN.equals(agencyUserBean.getRole())){
                currentAdmin = agencyUserBean;
            }
        }

        if(currentAdmin!=null){
            users.getUsers().remove(currentAdmin);
        }

        AgencyUserBean current = users.getCurrent();
        current.setRole(AgencyRole.ADMIN);
        users.getUsers().add(current);
    }

    public void addUser(AgencyEnrollCommand agencyEnrollCommand){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        AgencyUserBean current = users.getCurrent();
        current.setRole(AgencyRole.USER);
        users.getUsers().add(current);
    }

    public void deleteUser(AgencyEnrollCommand agencyEnrollCommand,String email){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        AgencyUserBean toDelete = null;
        for (AgencyUserBean agencyUserBean : users.getUsers()) {
            if(agencyUserBean.getEmail().equals(email)){
                toDelete = agencyUserBean;
            }
        }
        users.getUsers().remove(toDelete);
    }


    public void emptyCurrent(AgencyEnrollCommand agencyEnrollCommand){
        agencyEnrollCommand.getUsers().setCurrent(new AgencyUserBean());
    }


    public void fillAdmin(AgencyEnrollCommand agencyEnrollCommand){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        AgencyUserBean currentAdmin = null;
        for (AgencyUserBean agencyUserBean : users.getUsers()) {
            if(AgencyRole.ADMIN.equals(agencyUserBean.getRole())){
                currentAdmin = agencyUserBean;
            }
        }
        if(currentAdmin!=null)users.setCurrent(currentAdmin);
    }


    public List<String> availableFiles(AgencyEnrollFlowState state){
        return state.getRegistration().availableFiles();
    }


    public List<String> requiredFiles(AgencyEnrollFlowState state){
        return state.getRegistration().requiredFiles();
    }

    public Map<String,String> uploadedFileStatus(RequestContext context,AgencyEnrollFlowState state){

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


    public String saveFiles(RequestContext context,AgencyEnrollCommand agencyEnrollCommand,AgencyEnrollFlowState state){
        HttpSession session = ((HttpServletRequest)context.getExternalContext().getNativeRequest()).getSession();
        Map<String,FileCommand> uploaded = (Map<String, FileCommand>) session.getAttribute(UploadController.UPLOADED_PARAM);

        boolean needMoreFile = false;

        AttachementsCommand attachements = agencyEnrollCommand.getAttachements();
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
    public void createAccount(RequestContext context,AgencyEnrollCommand agencyEnrollCommand,AgencyEnrollFlowState state) throws ParseException, IOException {

        Agency agency = new Agency();
        InformationCommand info = agencyEnrollCommand.getInfo();
        AddressBean addressBean = info.getAddress();

        Address address = new Address();
        address.setCity(cityDao.findByZipcodeAndCityAndCountry_Code(addressBean.getZipcode(),addressBean.getCity(),addressBean.getCountry()));
        address.setCountry(countryDao.findByCode(addressBean.getCountry()));
        address.setAddress(addressBean.getStreet());

        agency.setAddress(address);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        agency.setCreatedDate(sdf.parse(info.getCreationDate()));

        agency.setLegalStatus(info.getLegalStatus());
        agency.setName(info.getName());
        agency.setPhone(info.getPhone());
        agency.setSiret(info.getSiret());


        Map<String, Attachement> fileUploaded = new HashMap<String, Attachement>();
        AttachementsCommand attachements = agencyEnrollCommand.getAttachements();
        Map<String, FileCommand> files = attachements.getFiles();
        for (Map.Entry<String, FileCommand> file : files.entrySet()) {
            String key = file.getKey();
            FileCommand value = file.getValue();
            if("LOGO".equals(key)){
                IhmConfig config = new IhmConfig();
                String logo = FileUtils.saveFile(value.getContent(), logoPath);
                config.setLogo(logo);
                agency.setIhmConfig(config);
                agency.setLogo(logo);
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

        agency.setAttachements(fileUploaded);

        agency = agencyDao.save(agency);

        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        List<AgencyUserBean> agencyUserBeans = users.getUsers();

        for (AgencyUserBean agencyUserBean : agencyUserBeans) {

            AgencyUser user = new AgencyUser();
            user.setAddress(null);

            user.setAccountExpired(false);
            user.setAccountLocked(false);
            user.setCredentialsExpired(false);
            user.setEnabled(false);

            user.setEmail(agencyUserBean.getEmail());
            user.setPassword(passwordEncoder.encodePassword(agencyUserBean.getPassword(),null));
            user.setPhone(agencyUserBean.getPhone());

            user.setUsername(agencyUserBean.getEmail());
            user.setFirstname(agencyUserBean.getFirstname());
            user.setLastname(agencyUserBean.getLastname());

            user.setRoles(Sets.<Role>newHashSet(roleDao.getRoleByName(agencyUserBean.getRole().getRoleDb())));

            user = (AgencyUser) userDao.save(user);

            agency.addUser(user);

            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getNativeRequest();
            Locale locale = request.getLocale();

            if(agencyUserBean.getRole().equals(AgencyRole.ADMIN)){
                //create admin user and send creation recap and valide account

                String url = agencyAdminConfirmBaseUrl + desCryptoService.generateOtpConfirm(agency.getName() + "|" + user.getEmail(), user, 48);

                Map<String, Object> model = new HashMap<String, Object>();

                model.put("url", url);
                model.put("user", user);
                model.put("agency", agency);

                Email email = Email.builder()
                        .subject(messageSource.getMessage("mail.enrolled.agency.admin", new Object[]{}, locale))
                        .model(model)
                        .content("email/agency_admin_enrolled")
                        .recipients(user.getEmail())
                        .noAttachements()
                        .build();
                try {
                    mailEngine.sendMessage(email, locale);
                } catch (SendException e) {
                    logger.error("error sending email", e);
                }
            }
        }



        agencyDao.save(agency);


    }

}
