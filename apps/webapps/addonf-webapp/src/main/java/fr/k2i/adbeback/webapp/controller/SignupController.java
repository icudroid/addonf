package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.service.PlayerManager;
import fr.k2i.adbeback.service.RoleManager;
import fr.k2i.adbeback.service.UserExistsException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 29/10/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/signup*")
public class SignupController {


    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private PlayerManager playerManager;

    private MessageSourceAccessor messages;

    @Autowired(required = false)
    Validator validator;

    @Autowired
    private IMailEngine mailEngine;

    @Autowired
    private DESCryptoService desCryptoService;

    @Value("${baseurl}")
    private String baseUrl;

    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }

    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }


    @ModelAttribute(value = "user")
    @RequestMapping(method = RequestMethod.GET)
    public Player showForm(Map<String, Object> model,HttpServletRequest request) {
        model.put("civilities", Sex.values());
        return new Player();
    }


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("user") Player user, final BindingResult errors,Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        model.put("civilities", Sex.values());

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);


            if (StringUtils.isBlank(user.getUsername())) {
                errors.rejectValue("username", "errors.required", new Object[] { getText("user.username.required", request.getLocale()) },
                        "Username is a required field.");
            }

            if (StringUtils.isBlank(user.getEmail())) {
                errors.rejectValue("email", "errors.required", new Object[] { getText("user.email.required", request.getLocale()) },
                        "Email is a required field.");
            }

            if (StringUtils.isBlank(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password.required", request.getLocale()) },
                        "Password is a required field.");
            }else if(user.getPassword().length()<=6){
                    errors.rejectValue("password", "errors.length", new Object[] { getText("user.password.length", request.getLocale()) },
                        "Password is a required min 7 characters");
            }


            if (user.getAddress().getCity().getId()==null) {
                errors.rejectValue("address", "errors.required", new Object[] { getText("user.address.required", request.getLocale()) },
                        "Address is a required field.");
            }

            if (user.getBirthday()==null) {
                errors.rejectValue("birthday", "errors.required", new Object[] { getText("user.birthday.required", request.getLocale()) },
                        "Birthday is a required field.");
            }


            if (errors.hasErrors()) {
                return "signup";
            }
        }

        final Locale locale = request.getLocale();

        user.setEnabled(false);

        // Set the default user role on this new user
        user.addRole(roleManager.getRole(Constants.USER_ROLE));

        // unencrypted users password to log in user automatically
        final String password = user.getPassword();

        try {
            playerManager.savePlayer(user);
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { user.getUsername(), user.getUsername() }, "duplicate user");
            errors.rejectValue("email", "errors.existing.user",
                    new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");

            return "signup";
        }

        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);


        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + user.getUsername() + "' an account information e-mail");
        }

        Map<String, Object> modelEmail = new HashMap<String, Object>();
        modelEmail.put("user", user.getUsername());


        //create users and send validate account when le administrator has validate her account
        String url = baseUrl + desCryptoService.generateOtpConfirm(user.getUsername() + "|" + user.getEmail(), user, 48);

        modelEmail.put("url",url);
        Email.Producer producer = Email.builder()
                .subject("Welcome")
                .model(modelEmail)
                .content("email/welcome")
                .recipients(user.getEmail())
                .noAttachements();

        try {
            mailEngine.sendMessage(producer.build(), Locale.FRANCE);
        } catch (Exception e) {
            log.debug("Une erreur est survenu leur de l'envoie de l'email", e);
        }


        return "success-registration";
    }




}
