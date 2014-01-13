package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.webapp.facade.BrandServiceFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 29/10/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HomeController {

    @Autowired
    private UserFacade userFacade;

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        try{
            Brand currentUser = userFacade.getCurrentUser();
            return "home";
        }catch (Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping("/logout-success")
    public String secure(Map<String, Object> model) {
        return "redirect:/";
    }
}
