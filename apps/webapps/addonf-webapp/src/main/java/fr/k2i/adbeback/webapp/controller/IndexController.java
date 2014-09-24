package fr.k2i.adbeback.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: dimitri
 * Date: 23/09/14
 * Time: 13:18
 * Goal:
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String slash(){
        return IMetaDataController.PathUtils.REDIRECT+"index.html";
    }


    @RequestMapping(value = "/index.html")
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/home.html")
    public String home(){
        return "home";
    }

}
