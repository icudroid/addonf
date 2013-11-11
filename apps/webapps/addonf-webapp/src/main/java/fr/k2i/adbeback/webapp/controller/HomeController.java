package fr.k2i.adbeback.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        return "home";
    }

    @RequestMapping("/game.html")
    public String game(Map<String, Object> model) {
        return "game";
    }


    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(Map<String, Object> model) {
        return "login";
    }

}
