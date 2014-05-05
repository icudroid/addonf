package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.user.Category;
import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.dao.ICategoryDao;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBean;
import fr.k2i.adbeback.webapp.bean.enroll.media.PriceInformationCommand;
import fr.k2i.adbeback.webapp.facade.MediaFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: dimitri
 * Date: 05/05/14
 * Time: 13:38
 * Goal:
 */
@Controller
public class SettingMediaController {

    @Autowired
    private MediaFacadeService mediaFacadeService;


    @Autowired
    private ICategoryDao categoryDao;

    @RequestMapping(value = IMetaDataController.Path.SHOW_PASSPHRASE,method = RequestMethod.GET)
    public String showPassphrase(ModelMap model) throws Exception {
        model.put("passphrase",mediaFacadeService.getMediaPassPhrase());
        return IMetaDataController.View.SHOW_PASSPHRASE;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_PASSPHRASE_GENERATE_NEW,method = RequestMethod.GET)
    public String generatePassphrase(ModelMap model) throws Exception {
        model.put("passphrase",mediaFacadeService.generateNewPassPhrase());
        return IMetaDataController.View.SHOW_PASSPHRASE;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_EXT_ID,method = RequestMethod.GET)
    public String showExtId(ModelMap model) throws Exception {
        model.put("extId",mediaFacadeService.getExtId());
        return IMetaDataController.View.EXT_ID;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_MY_SERVICES,method = RequestMethod.GET)
    public String showMyServices(@ModelAttribute("categoryPriceBean") CategoryPriceBean categoryPriceBean,ModelMap model,HttpServletRequest request) throws Exception {
        PriceInformationCommand prices = mediaFacadeService.getServices();
        request.getSession().setAttribute("prices", prices);
        model.put("prices",prices);
        List<Category> categories = categoryDao.getAll();
        model.put("categories",categories);

        model.put("medias", MediaType.values());
        return IMetaDataController.View.MY_SERVICES;
    }



    @RequestMapping(value = IMetaDataController.Path.SHOW_MY_SERVICES,method = RequestMethod.POST,params ={"addService"} )
    public String addService(@ModelAttribute("categoryPriceBean") CategoryPriceBean categoryPriceBean,ModelMap model,HttpServletRequest request) throws Exception {
        PriceInformationCommand prices = (PriceInformationCommand) request.getSession().getAttribute("prices");
        mediaFacadeService.addService(prices,categoryPriceBean);


        model.put("prices",prices);
        List<Category> categories = categoryDao.getAll();
        model.put("categories",categories);
        model.put("medias", MediaType.values());

        model.put("categoryPriceBean",new CategoryPriceBean());

        return IMetaDataController.View.MY_SERVICES;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_MY_SERVICES,method = RequestMethod.GET,params ={"removeService"} )
    public String rmService(@ModelAttribute("categoryPriceBean") CategoryPriceBean categoryPriceBean,@RequestParam("uid") String uid,ModelMap model,HttpServletRequest request) throws Exception {
        PriceInformationCommand prices = (PriceInformationCommand) request.getSession().getAttribute("prices");
        mediaFacadeService.rmService(prices,uid);

        model.put("prices",prices);
        List<Category> categories = categoryDao.getAll();
        model.put("categories",categories);

        model.put("medias", MediaType.values());

        return IMetaDataController.View.MY_SERVICES;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_MY_SERVICES,method = RequestMethod.POST,params ={"saveService"} )
    public String saveService(@ModelAttribute("categoryPriceBean") CategoryPriceBean categoryPriceBean,ModelMap model,HttpServletRequest request) throws Exception {
        PriceInformationCommand prices = (PriceInformationCommand) request.getSession().getAttribute("prices");
        mediaFacadeService.saveServices(prices);

        prices = mediaFacadeService.getServices();
        request.getSession().setAttribute("prices", prices);
        model.put("prices",prices);


        List<Category> categories = categoryDao.getAll();
        model.put("categories",categories);

        model.put("medias", MediaType.values());

        return IMetaDataController.View.MY_SERVICES;
    }



}
