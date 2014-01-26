package fr.k2i.adbeback.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 26/01/14
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AdsController {

    @RequestMapping("/ads")
    public String ads(){
        return "ads";
    }
}
