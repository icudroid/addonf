package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.CartBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class CartController extends AbstractController{


    @RequestMapping("/cart.html")
    public String cart(Map<String, Object> model,HttpServletRequest request) throws Exception {
        return "cart";
    }


}
