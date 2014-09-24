package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.transaction.Wallet;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.ICityDao;
import fr.k2i.adbeback.dao.IPlayerDao;
import fr.k2i.adbeback.dao.jpa.CityDao;
import fr.k2i.adbeback.service.PlayerManager;
import fr.k2i.adbeback.service.RoleManager;
import fr.k2i.adbeback.service.UserExistsException;
import fr.k2i.adbeback.service.exception.EnrollException;
import fr.k2i.adbeback.webapp.bean.PlayerForm;
import fr.k2i.adbeback.webapp.facade.OtpServiceFacade;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ICityDao cityDao;

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


    @Autowired
    private OtpServiceFacade otpServiceFacade;

    @Value("${addonf.base.confirm.url}")
    private String confirmEnrollUrl;

    @Autowired
    private IPlayerDao playerDao;

    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }

    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }


    @ModelAttribute(value = "user")
    @RequestMapping(method = RequestMethod.GET)
    public PlayerForm showForm(Map<String, Object> model,HttpServletRequest request) {
        model.put("civilities", Sex.values());
        return new PlayerForm();
    }


    @InitBinder("user")
    public void initBinder(WebDataBinder binder,Locale locale) {
        //DateFormat dateFormat = new SimpleDateFormat(messageSource.getMessage("date_format",null,locale));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("user") PlayerForm user, final BindingResult errors,Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        model.put("civilities", Sex.values());

        //City city = user.getAddress().getCity();
        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);

            if (StringUtils.isBlank(user.getUsername())) {
                errors.rejectValue("username", "errors.required", new Object[] { getText("user.username.required", request.getLocale()) },
                        "Username is a required field.");
            }else{
                if(playerDao.findByEmailorUserName(user.getUsername())!=null){
                    errors.rejectValue("username", "errors.exist", new Object[] { getText("user.username.exist", request.getLocale()) },
                            "Username is a required field.");
                }
            }

            if (StringUtils.isBlank(user.getEmail())) {
                errors.rejectValue("email", "errors.required", new Object[]{getText("user.email.required", request.getLocale())},
                        "Email is a required field.");
            }else{
                    if(playerDao.findByEmailorUserName(user.getEmail())!=null){
                        errors.rejectValue("email", "errors.exist", new Object[] { getText("user.email.required", request.getLocale()) },
                                "Username is a required field.");
                    }
            }


            if (StringUtils.isBlank(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password.required", request.getLocale()) },
                        "Password is a required field.");
            }else if(user.getPassword().length()<=6){
                    errors.rejectValue("password", "errors.length", new Object[] { getText("user.password.length", request.getLocale()) },
                        "Password is a required min 7 characters");
            }


            if (user.getCityId()==null) {
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

        Player player = new Player();
        player.setUsername(user.getUsername());
        player.setBirthday(user.getBirthday());
        player.setNewsletter(user.isNewsletter());
        player.setEmail(user.getEmail());
        player.setSex(user.getSex());

        player.setWallet(new Wallet());

        player.setEnabled(false);

        // Set the default user role on this new user
        player.addRole(roleManager.getRole(Constants.USER_ROLE));

        // unencrypted users password to log in user automatically
        final String password = user.getPassword();
        player.setPassword(password);

        Address address = new Address();
        City city = cityDao.get(user.getCityId());
        address.setCity(city);
        address.setCountry(city.getCountry());
        player.setAddress(address);

        try {
            player = playerManager.savePlayer(player);
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
        String url = confirmEnrollUrl + desCryptoService.generateOtpConfirm(player.getUsername() + "|" + player.getEmail(), player, 48);

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


    @RequestMapping(value = "/enrollConfirm/{encoded}/{key}")
    public String confirm(@PathVariable("encoded") String encoded,@PathVariable("key") String key,ModelMap modelMap){

        String decrypt = desCryptoService.decrypt(encoded);
        String[] split = decrypt.split("|");
        String username = split[0];
        String email = split[1];

        OtpServiceFacade.ConfirmationRegistration res = otpServiceFacade.confirmRegistration(email, username, key);


        modelMap.addAttribute("result", res.name());
        modelMap.addAttribute("email",email);


        switch (res){
            case TIME_OUT:
                return IMetaDataController.View.REGISTRATION_TIMEOUT;
            case OK:
                User user = playerDao.findByEmailorUserName(email);
                playerDao.enable(user.getId());
                otpServiceFacade.removeOtp(email,username,key);
                return IMetaDataController.View.REGISTRATION_CONFIRM;
            case KO:
                return IMetaDataController.View.REGISTRATION_KO;
            default:
                return IMetaDataController.View.REGISTRATION_KO;
        }


    }



}
