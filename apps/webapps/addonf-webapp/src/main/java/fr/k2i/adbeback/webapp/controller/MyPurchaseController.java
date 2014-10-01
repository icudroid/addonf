package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import fr.k2i.adbeback.webapp.bean.OrderBean;
import fr.k2i.adbeback.webapp.facade.CreditFacadeService;
import fr.k2i.adbeback.webapp.facade.PurchaseFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:51
 * Goal:
 */
@Controller
public class MyPurchaseController {


    @Autowired
    private PurchaseFacadeService purchaseFacadeService;


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyPurchaseController.Path.MY_PURCHASE)
    public String show(ModelMap modelMap){
        return IMetaDataController.MyPurchaseController.View.SHOW;
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyPurchaseController.Path.GET_PURCHASES)
    @ResponseBody
    public Page<HistoryAdGameBean> getPurchases(Pageable pageable){
        return purchaseFacadeService.getPurchases(pageable);
    }

    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyPurchaseController.Path.DETAIL)
    public String getPurchaseDetail(@PathVariable Long id,ModelMap modelMap){
        modelMap.addAttribute("detail",purchaseFacadeService.getPurchaseDetail(id));
        return IMetaDataController.MyPurchaseController.View.DETAIL;
    }


}
