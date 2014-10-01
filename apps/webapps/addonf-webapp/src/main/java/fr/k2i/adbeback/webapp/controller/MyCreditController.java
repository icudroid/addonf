package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.EmpreintSmallBean;
import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import fr.k2i.adbeback.webapp.facade.BorrowFacadeService;
import fr.k2i.adbeback.webapp.facade.CreditFacadeService;
import fr.k2i.adbeback.webapp.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:51
 * Goal:
 */
@Controller
public class MyCreditController {


    @Autowired
    private CreditFacadeService creditFacadeService;


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyCreditController.Path.MY_CREDIT)
    public String show(ModelMap modelMap){
        modelMap.addAttribute("adAmount", creditFacadeService.getAdAmount());
        return IMetaDataController.MyCreditController.View.SHOW;
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyCreditController.Path.GET_CREDITS)
    @ResponseBody
    public Page<HistoryAdGameBean> getCredits(Pageable pageable){
        return creditFacadeService.getCreditGame(pageable);
    }




}
