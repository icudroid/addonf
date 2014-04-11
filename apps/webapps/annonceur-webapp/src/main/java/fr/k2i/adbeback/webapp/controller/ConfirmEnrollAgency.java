package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.user.Agency;
import fr.k2i.adbeback.core.business.user.AgencyUser;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IWebUserDao;
import fr.k2i.adbeback.webapp.bean.enroll.agency.UserAgencyConfirmCommand;
import fr.k2i.adbeback.webapp.facade.AgencyServiceFacade;
import fr.k2i.adbeback.webapp.facade.OtpServiceFacade;
import fr.k2i.adbeback.webapp.validator.UserAgencyConfirmCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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


        if(res.equals(OtpServiceFacade.ConfirmationRegistration.OK)){
            //send user mail confirmation
            AgencyUser user = (AgencyUser) webUserDao.getUserByEmail(email);
            Agency agency = user.getAgency();
            agencyServiceFacade.sendAgencyUserValidation(agency.getId(),req.getLocale());
            otpServiceFacade.removeOtp(email,name,code);
        }


        return IMetaDataController.View.REGISTRATION_AGENCY_ADMIN_CONFIRM;


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
        return IMetaDataController.View.REGISTRATION_AGENCY_USER_CONFIRM;

    }

    @Autowired
    private UserAgencyConfirmCommandValidator userAgencyConfirmCommand;

    @InitBinder("userAgencyConfirmCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userAgencyConfirmCommand);
    }

    @RequestMapping(value = IMetaDataController.Path.REGISTRATION_AGENCY_USER_CONFIRM,method = RequestMethod.POST)
    public String confirmUserRegistrationSubmit(@Valid @ModelAttribute("userAgencyConfirmCommand") UserAgencyConfirmCommand userAgencyConfirmCommand,@PathVariable String crypt,@PathVariable String code,ModelMap modelMap, HttpServletRequest req) throws Exception {
        String[] strings = desCryptoService.decrypt(crypt).split("\\|");

        String name = strings[0];
        String email = strings[1];

        OtpServiceFacade.ConfirmationRegistration res = otpServiceFacade.confirmRegistration(email,name,code);
        otpServiceFacade.removeOtp(email,name,code);


        HttpSession session = req.getSession();
        session.setAttribute("result",res.name());
        session.setAttribute("email",email);

        return IMetaDataController.View.REGISTRATION_AGENCY_USER_CONFIRM;

    }

}
