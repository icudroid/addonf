package fr.k2i.adbeback.webapp.helper;

import com.google.common.collect.Sets;
import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import fr.k2i.adbeback.webapp.bean.enroll.AttachementsCommand;
import fr.k2i.adbeback.webapp.bean.enroll.InformationCommand;
import fr.k2i.adbeback.webapp.bean.enroll.media.MediaEnrollCommand;
import fr.k2i.adbeback.webapp.bean.enroll.media.MediaUserBean;
import fr.k2i.adbeback.webapp.controller.UploadController;
import fr.k2i.adbeback.webapp.facade.FileUtils;
import fr.k2i.adbeback.webapp.state.enroll.MediaEnrollFlowState;
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
public class MediaEnrollHelper {

    private static Logger logger = LogHelper.getLogger(MediaEnrollHelper.class);

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

    @Value("${media.base.user.confirm.url}")
    private String mediaUserConfirmBaseUrl;


    @Autowired
    private MessageSource messageSource;


    @Autowired
    private IMediaDao mediaDao;

    @Resource(name = "annonceurUserDao")
    private IWebUserDao userDao;



    public List<String> availableFiles(MediaEnrollFlowState state){
        return state.getRegistration().availableFiles();
    }

    public List<String> requiredFiles(MediaEnrollFlowState state){
        return state.getRegistration().requiredFiles();
    }

    public Map<String,String> uploadedFileStatus(RequestContext context,MediaEnrollFlowState state){

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


    public String saveFiles(RequestContext context,MediaEnrollCommand mediaEnrollCommand,MediaEnrollFlowState state){
        HttpSession session = ((HttpServletRequest)context.getExternalContext().getNativeRequest()).getSession();
        Map<String,FileCommand> uploaded = (Map<String, FileCommand>) session.getAttribute(UploadController.UPLOADED_PARAM);

        boolean needMoreFile = false;

        AttachementsCommand attachements = mediaEnrollCommand.getAttachements();
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
    public void createAccount(RequestContext context,MediaEnrollCommand mediaEnrollCommand,MediaEnrollFlowState state) throws ParseException, IOException {

        Media media = new Media();
        InformationCommand info = mediaEnrollCommand.getInfo();
        AddressBean addressBean = info.getAddress();

        Address address = new Address();
        address.setCity(cityDao.findByZipcodeAndCityAndCountry_Code(addressBean.getZipcode(), addressBean.getCity(), addressBean.getCountry()));
        address.setCountry(countryDao.findByCode(addressBean.getCountry()));
        address.setAddress(addressBean.getStreet());

        media.setAddress(address);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        media.setCreatedDate(sdf.parse(info.getCreationDate()));

        media.setLegalStatus(info.getLegalStatus());
        media.setName(info.getName());
        media.setPhone(info.getPhone());
        media.setSiret(info.getSiret());


        Map<String, Attachement> fileUploaded = new HashMap<String, Attachement>();
        AttachementsCommand attachements = mediaEnrollCommand.getAttachements();
        Map<String, FileCommand> files = attachements.getFiles();
        for (Map.Entry<String, FileCommand> file : files.entrySet()) {
            String key = file.getKey();
            FileCommand value = file.getValue();
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

        media.setAttachements(fileUploaded);


        media = mediaDao.save(media);

        MediaUserBean mediaUserBean = mediaEnrollCommand.getUser();

            MediaUser user = new MediaUser();
            user.setAddress(null);

            user.setAccountExpired(false);
            user.setAccountLocked(false);
            user.setCredentialsExpired(false);
            user.setEnabled(false);

            user.setEmail(mediaUserBean.getEmail());
            user.setPassword(passwordEncoder.encodePassword(mediaUserBean.getPassword(), null));
            user.setPhone(mediaUserBean.getPhone());

            user.setUsername(mediaUserBean.getEmail());
            user.setFirstname(mediaUserBean.getFirstname());
            user.setLastname(mediaUserBean.getLastname());

            user.setRoles(Sets.<Role>newHashSet(roleDao.getRoleByName(Constants.MEDIA_ROLE)));

            user = (MediaUser) userDao.save(user);
            media.setUser(user);


            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getNativeRequest();
            Locale locale = request.getLocale();


            String url = mediaUserConfirmBaseUrl + desCryptoService.generateOtpConfirm(media.getName() + "|" + user.getEmail(), user, 48);

            Map<String, Object> model = new HashMap<String, Object>();

            model.put("url", url);
            model.put("user", user);
            model.put("media", media);

            Email email = Email.builder()
                    .subject(messageSource.getMessage("mail.enrolled.media", new Object[]{}, locale))
                    .model(model)
                    .content("email/media_user_enrolled")
                    .recipients(user.getEmail())
                    .noAttachements()
                    .build();
            try {
                mailEngine.sendMessage(email, locale);
            } catch (SendException e) {
                logger.error("error sending email", e);
            }

        mediaDao.save(media);


    }


}
