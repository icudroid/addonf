package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.service.PlayerManager;
import fr.k2i.adbeback.service.RoleManager;
import fr.k2i.adbeback.service.UserExistsException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }

    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }


    @ModelAttribute(value = "player")
    @RequestMapping(method = RequestMethod.GET)
    public Player showForm(Map<String, Object> model) {
        model.put("civilities", Sex.values());
        return new Player();
    }


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(final Player player, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        if (validator != null) { // validator is null during testing
            validator.validate(player, errors);

            if (StringUtils.isBlank(player.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password", request.getLocale()) },
                        "Password is a required field.");
            }

            if (errors.hasErrors()) {
                return "signup";
            }
        }

        final Locale locale = request.getLocale();

        player.setEnabled(true);

        // Set the default user role on this new user
        player.addRole(roleManager.getRole(Constants.USER_ROLE));

        // unencrypted users password to log in user automatically
        final String password = player.getPassword();

        try {
            playerManager.savePlayer(player);
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { player.getUsername(), player.getEmail() }, "duplicate user");

            return "signup";
        }

        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);


        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + player.getUsername() + "' an account information e-mail");
        }

        Map<String, Object> model = new HashMap<String, Object>();

        Email.Producer producer = Email.builder(true)
                .subject("Récupération de votre mot de passe")
                .model(model)
                .content("forgotten")
                .recipients(player.getEmail())
                .noAttachements();

        try {
            mailEngine.sendMessage(producer.build(), Locale.FRANCE);
        } catch (Exception e) {
            log.debug("Une erreur est survenu leur de l'envoie de l'email", e);
        }


        return "success-registration";
    }




}
