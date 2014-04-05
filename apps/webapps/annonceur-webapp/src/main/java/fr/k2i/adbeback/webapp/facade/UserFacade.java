package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.otp.OTPUserSecurityConfirm;
import fr.k2i.adbeback.core.business.player.WebUser;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.IOTPSecurityDao;
import fr.k2i.adbeback.service.BrandManager;
import fr.k2i.adbeback.webapp.bean.ChangePwdBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserFacade {

    @Autowired
    private BrandManager brandManager;

    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IMailEngine mailEngine;


    @Value("${addonf.logo.location}")
    private String logoPath;


    @Value("${addonf.base.url}")
    private String urlBase;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private IOTPSecurityDao securityDao;

    @Transactional
    public Brand getCurrentUser() throws Exception{
        Object principal = getAuthenticationPlayer().getPrincipal();
        if (!(principal instanceof WebUser)) {
            throw new Exception("Please check configuration. Should be Brand in the principal.");
        }

        return brandDao.get(((WebUser) principal).getUser().getId());
    }

    protected Authentication getAuthenticationPlayer() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Transactional
    public void setLogo(MultipartFile logo) throws Exception {
        Brand currentUser = getCurrentUser();
        currentUser.setLogo(FileUtils.saveFile(logo.getBytes(),logoPath));
    }






    @Transactional
    public void sendForgottenPasswd(String username,HttpServletRequest request) throws SendException {
/*
        Brand user = brandDao.findByEmail(username);

        if(user != null){

            Map<String, Object> modelEmail = new HashMap<String, Object>();
            String endUrl = desCryptoService.generateOtpConfirm(user.getUsername(), user, 48);
            modelEmail.put("name",user.getUsername());
            modelEmail.put("url",urlBase+"pwdinit/"+endUrl);
            Email forgottenPasswd = Email.builder()
                    .subject("Mot de passe oublié")
                    .model(modelEmail)
                    .content("email/forgottenPasswd")
                    .recipients(user.getEmail())
                    .noAttachements()
                    .build();

            try{
                mailEngine.sendMessage(forgottenPasswd,request.getLocale());
            }catch (Exception e){

            }
        }
*/

    }



    @Transactional
    public boolean isValidateOtp(String username, String key){
/*        Brand user = brandDao.findByEmail(username);
        if(user ==null){
            return false;
        }
        OTPUserSecurityConfirm otp = securityDao.findByBrand(user);
        if(otp == null){
            return false;
        }
        if(!otp.getKey().equals(key)){
            return false;
        }*/

        return true;
    }


    @Transactional
    public void changePasswd(String username, String password) {
        brandManager.changePasswd(username, password);
    }

    @Transactional
    public void changePasswd(String password) throws Exception {
        //brandManager.changePasswd(getCurrentUser(), password);
    }

    public void validateChangePwdBean(ChangePwdBean changePwdBean, Errors errors,Locale locale) throws Exception {

/*        if(!errors.hasErrors()){
            String password = getCurrentUser().getPassword();
            String old = passwordEncoder.encodePassword(changePwdBean.getOldPassword(), null);
            if(!password.equals(old)){
                errors.rejectValue("oldPassword","notMatch",messageSource.getMessage("addonf.notMatch",null,locale));
            }

            if(!changePwdBean.getNewConfirmPassword().equals(changePwdBean.getNewPassword())){
                errors.rejectValue("newConfirmPassword","notMatch",messageSource.getMessage("addonf.notMatch",null,locale));
            }
        }*/

    }

}
