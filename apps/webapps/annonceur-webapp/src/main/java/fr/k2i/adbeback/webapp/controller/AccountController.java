package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.ChangePwdBean;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.validator.ChangePwdBeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @InitBinder("pwd")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(changePwdBeanValidator);
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

}
