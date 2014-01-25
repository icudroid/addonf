package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.otp.OneTimePassword;
import fr.k2i.adbeback.core.business.otp.OtpAction;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.WebPlayer;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IOneTimePasswordDao;
import fr.k2i.adbeback.dao.IPlayerDao;
import fr.k2i.adbeback.service.PlayerManager;
import fr.k2i.adbeback.webapp.bean.myaccount.ChangePwdBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
public class PlayerFacade {


    @Autowired
    private IPlayerDao playerDao;

    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IOneTimePasswordDao oneTimePasswordDao;

    @Autowired
    private IMailEngine mailEngine;

    @Autowired
    private PlayerManager playerManager;

    @Value("${addonf.base.url}")
    private String urlBase;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Player getCurrentPlayer() {
        Object principal = getAuthenticationPlayer().getPrincipal();
        if (!(principal instanceof WebPlayer)) {
            throw new AssertionError("Please check configuration. Should be Player in the principal.");
        }

        return playerDao.get(((WebPlayer) principal).getUser().getId());
    }

    protected Authentication getAuthenticationPlayer() {
        return SecurityContextHolder.getContext().getAuthentication();
    }




    @Transactional
    public void sendForgottenPasswd(String username,HttpServletRequest request) throws SendException {
        Player user = playerDao.findByEmailorUserName(username);

        if(user != null){

            Map<String, Object> modelEmail = new HashMap<String, Object>();
            String endUrl = desCryptoService.generateOtp(user.getUsername(),user, OtpAction.FORGOTTEN_PWD);
            modelEmail.put("name",user.getUsername());
            modelEmail.put("url",urlBase+"pwdinit/"+endUrl);
            Email forgottenPasswd = Email.builder()
                    .subject("Mot de passe oublié")
                    .model(modelEmail)
                    .content("email/forgottenPasswd")
                    .recipients(user.getEmail())
                    .noAttachements()
                    .build();

            mailEngine.sendMessage(forgottenPasswd,request.getLocale());
        }

    }



    @Transactional
    public boolean isValidateOtp(String username, String key){
        Player user = playerDao.findByEmailorUserName(username);
        if(user ==null){
            return false;
        }
        OneTimePassword otp = oneTimePasswordDao.findBy(user, OtpAction.FORGOTTEN_PWD);
        if(otp == null){
            return false;
        }
        if(!otp.getKey().equals(key)){
            return false;
        }

        return true;
    }


    @Transactional
    public void changePasswd(String username, String password) {
        playerManager.changePasswd(username, password);
    }

    @Transactional
    public void changePasswd(String password) {
        playerManager.changePasswd(getCurrentPlayer(), password);
    }

    public void validateChangePwdBean(ChangePwdBean changePwdBean, Errors errors,Locale locale) {

        if(!errors.hasErrors()){
            String password = getCurrentPlayer().getPassword();
            String old = passwordEncoder.encodePassword(changePwdBean.getOldPassword(), null);
            if(!password.equals(old)){
                errors.rejectValue("oldPassword","notMatch",messageSource.getMessage("addonf.notMatch",null,locale));
            }

            if(!changePwdBean.getNewConfirmPassword().equals(changePwdBean.getNewPassword())){
                errors.rejectValue("newConfirmPassword","notMatch",messageSource.getMessage("addonf.notMatch",null,locale));
            }
         }

    }
}
