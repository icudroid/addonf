package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.user.BrandUser;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.webapp.facade.FileUtils;
import fr.k2i.adbeback.webapp.facade.StatisticsFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

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
            if(userFacade.getCurrentUser()!=null){
                return "home";
            }else{
                return "index";
            }

        }catch (Exception e){
            return "redirect:/index.html";
        }
    }


    @RequestMapping("/index.html")
    public String index(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping("/logout-success")
    public String secure(Map<String, Object> model) {
        return "redirect:/";
    }


    @RequestMapping(value="/setLogo",method = RequestMethod.GET)
    public String setLogo(Map<String, Object> model) throws Exception {

        User user = userFacade.getCurrentUser();
        if (user instanceof BrandUser) {
            BrandUser brandUser = (BrandUser) user;
            model.put("logo", brandUser.getBrand().getLogo());
        }
        return "logo";
    }


    @RequestMapping(value="/setLogo",method = RequestMethod.POST)
    public String setLogoSubmit(MultipartFile logo) throws Exception {
        userFacade.setLogo(logo);
        return IMetaDataController.PathUtils.REDIRECT;
    }

}
