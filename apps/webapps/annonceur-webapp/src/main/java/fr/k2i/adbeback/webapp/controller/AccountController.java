package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.webapp.bean.ChangeIdentifiantBean;
import fr.k2i.adbeback.webapp.bean.ChangePwdBean;
import fr.k2i.adbeback.webapp.facade.OtpServiceFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.validator.ChangeIdentifiantBeanValidator;
import fr.k2i.adbeback.webapp.validator.ChangePwdBeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * User: dimitri
 * Date: 06/05/14
 * Time: 11:18
 * Goal:
 */
@Controller
public class AccountController {

    @Autowired
    private UserFacade userFacade;


    @Autowired
    private ChangePwdBeanValidator changePwdBeanValidator;

    @Autowired
    private ChangeIdentifiantBeanValidator changeIdentifiantBeanValidator;

    @Autowired
    private DESCryptoService desCryptoService;
    
    @Autowired
    private OtpServiceFacade otpServiceFacade;


    @InitBinder("pwd")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(changePwdBeanValidator);
    }

    @InitBinder("id")
    protected void initBinderId(WebDataBinder binder) {
        binder.setValidator(changeIdentifiantBeanValidator);
    }

    @RequestMapping(value = "/account")
    public String showAccount(){
        return "account/index";
    }

    @RequestMapping(value = "/account/pwd",method = RequestMethod.GET)
    public String showChangePwd(@ModelAttribute("pwd")ChangePwdBean changePwdBean){
        return "account/pwd";
    }

    @RequestMapping(value = "/account/pwd",method = RequestMethod.POST)
    public String changePwd(@Valid @ModelAttribute("pwd")ChangePwdBean changePwdBean,BindingResult bindingResult,HttpServletRequest request) throws Exception {

        userFacade.validateChangePwdBean(changePwdBean,bindingResult,request.getLocale());

        if(bindingResult.hasErrors()){
            return "account/pwd";
        }else{
            userFacade.changePasswd(changePwdBean.getNewPassword());
            return "account/pwdChanged";
        }
    }


    @RequestMapping(value = "/account/id",method = RequestMethod.GET)
    public String showChangeIdentifiant(@ModelAttribute("id")ChangeIdentifiantBean identifiantBean){


        return "account/id";
    }


    @RequestMapping(value = "/account/id",method = RequestMethod.POST)
    public String changeIdentifiantSubmit(@Valid @ModelAttribute("id")ChangeIdentifiantBean identifiantBean,BindingResult bindingResult,HttpServletRequest request) throws Exception {
        if(bindingResult.hasErrors()){
            return "account/id";
        }else {
            userFacade.actionForChangeId(identifiantBean.getEmail(),request.getLocale());
            return "account/ChangeIdwaitForUserAction";
        }
    }


    @RequestMapping(value = "/account/confirmChId/{crypt}/{key}",method = RequestMethod.POST)
    public String changeIdentifiantSubmit(@PathVariable String crypt,@PathVariable String key){
        String[] strings = desCryptoService.decrypt(crypt).split("\\|");

        String oldEmail = strings[0];
        String newEmail = strings[1];

        OtpServiceFacade.ConfirmationRegistration res = otpServiceFacade.confirmRegistration(oldEmail,null,key);

        switch (res){
            case TIME_OUT:
                return "account/timeout";
            case OK:
                userFacade.changeId(oldEmail,newEmail);
                otpServiceFacade.removeOtp(oldEmail, null, key);
                return "account/changeIdConfirmed";
            case KO:
                return "account/timeout";
            default:
                return "account/timeout";
        }

    }




}
