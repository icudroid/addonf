package fr.k2i.adbeback.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: dimitri
 * Date: 19/09/14
 * Time: 12:06
 * Goal:
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
}
