package fr.k2i.adbeback.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: dimitri
 * Date: 14/05/14
 * Time: 17:21
 * Goal:
 */
@Controller
public class BillingController {

    @RequestMapping(value = IMetaDataController.Path.BILLING,method = RequestMethod.GET)
    public String showBillings(){
        return IMetaDataController.View.BILLING;
    }
}
