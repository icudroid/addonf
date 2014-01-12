package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.CartBean;
import fr.k2i.adbeback.webapp.facade.MediaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 29/10/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProductController extends AbstractController{


    @Autowired
    private MediaFacade mediaFacade;


    @RequestMapping("/product.html")
    public String checkout(@RequestParam("musicId") Long musicId, Map<String, Object> model,HttpServletRequest request) throws Exception {
        model.put("media",mediaFacade.getMediaById(musicId));
        return "product";
    }


}
