package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.user.Agency;
import fr.k2i.adbeback.core.business.user.AgencyUser;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IWebUserDao;
import fr.k2i.adbeback.webapp.bean.ChangePasswordBean;
import fr.k2i.adbeback.webapp.bean.enroll.agency.UserAgencyConfirmCommand;
import fr.k2i.adbeback.webapp.facade.AgencyServiceFacade;
import fr.k2i.adbeback.webapp.facade.OtpServiceFacade;
import fr.k2i.adbeback.webapp.validator.UserAgencyConfirmCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * User: dimitri
 * Date: 10/04/14
 * Time: 14:37
 * Goal:
 */
@Controller
public class ConfirmEnrollAgency {

    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private OtpServiceFacade otpServiceFacade;


    @Resource(name = "annonceurUserDao")
    private IWebUserDao webUserDao;

    @Autowired
    private AgencyServiceFacade agencyServiceFacade;



    @RequestMapping(value = IMetaDataController.Path.REGISTRATION_AGENCY_ADMIN_CONFIRM)
    public String confirmAdminRegistration(@PathVariable String crypt,@PathVariable String code,ModelMap modelMap, HttpServletRequest req) throws Exception {
        String[] strings = desCryptoService.decrypt(crypt).split("\\|");

        String name = strings[0];
        String email = strings[1];

        OtpServiceFacade.ConfirmationRegistration res = otpServiceFacade.confirmRegistration(email,name,code);
        HttpSession session = req.getSession();
        session.setAttribute("result",res.name());
        session.setAttribute("email",email);


        switch (res){
            case TIME_OUT:
                return IMetaDataController.View.REGISTRATION_TIMEOUT;
            case OK:
                //send user mail confirmation
                AgencyUser user = (AgencyUser) webUserDao.getUserByEmail(email);
                Agency agency = user.getAgency();
                webUserDao.enable(user.getId());
                agencyServiceFacade.sendAgencyUserValidation(agency.getId(),req.getLocale());
                otpServiceFacade.removeOtp(email,name,code);
                return IMetaDataController.View.REGISTRATION_AGENCY_ADMIN_CONFIRM;
            case KO:
                return IMetaDataController.View.REGISTRATION_KO;
            default:
                return IMetaDataController.View.REGISTRATION_KO;
        }


    }


    @RequestMapping(value = IMetaDataController.Path.REGISTRATION_AGENCY_USER_CONFIRM,method = RequestMethod.GET)
    public String confirmUserRegistration(@PathVariable String crypt,@PathVariable String code,ModelMap modelMap, HttpServletRequest req) throws Exception {
        String[] strings = desCryptoService.decrypt(crypt).split("\\|");

        String name = strings[0];
        String email = strings[1];

        OtpServiceFacade.ConfirmationRegistration res = otpServiceFacade.confirmRegistration(email,name,code);

        HttpSession session = req.getSession();
        session.setAttribute("result",res.name());
        session.setAttribute("email",email);

        modelMap.put("userAgencyConfirmCommand",new UserAgencyConfirmCommand(email));
        switch (res){
            case TIME_OUT:
                return IMetaDataController.View.REGISTRATION_TIMEOUT;
            case OK:
                return IMetaDataController.View.REGISTRATION_AGENCY_USER_CONFIRM;
            case KO:
                return IMetaDataController.View.REGISTRATION_KO;
            default:
                return IMetaDataController.View.REGISTRATION_KO;
        }

    }

    @Autowired
    private UserAgencyConfirmCommandValidator userAgencyConfirmCommand;

    @InitBinder("userAgencyConfirmCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userAgencyConfirmCommand);
    }

    @RequestMapping(value = IMetaDataController.Path.REGISTRATION_AGENCY_USER_CONFIRM,method = RequestMethod.POST)
    public String confirmUserRegistrationSubmit(@PathVariable("crypt") String crypt,@PathVariable("code") String code,@Valid @ModelAttribute("userAgencyConfirmCommand") UserAgencyConfirmCommand userAgencyConfirmCommand,BindingResult bindingResult,Map<String, Object> model) {

        String[] strings = desCryptoService.decrypt(crypt).split("\\|");

        String name = strings[0];
        String email = strings[1];

        OtpServiceFacade.ConfirmationRegistration res = otpServiceFacade.confirmRegistration(email,name,code);
        model.put("result",res.name());
        model.put("email",email);

        switch (res){
            case TIME_OUT:
                return IMetaDataController.View.REGISTRATION_TIMEOUT;
            case OK:
                if(!bindingResult.hasErrors()){
                    if(res.equals(OtpServiceFacade.ConfirmationRegistration.OK)){
                        Long id = webUserDao.getUserByEmail(email).getId();
                        webUserDao.setPassword(id,userAgencyConfirmCommand.getPassword());
                        webUserDao.enable(id);
                        otpServiceFacade.removeOtp(email,name,code);
                        return IMetaDataController.View.REGISTRATION_AGENCY_USER_CONFIRMED;
                    }
                }
                return IMetaDataController.View.REGISTRATION_AGENCY_USER_CONFIRM;
            case KO:
                return IMetaDataController.View.REGISTRATION_KO;
            default:
                return IMetaDataController.View.REGISTRATION_KO;
        }


    }

}
