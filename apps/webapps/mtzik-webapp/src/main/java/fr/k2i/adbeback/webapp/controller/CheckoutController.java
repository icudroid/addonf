package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.jpa.GenreRepository;
import fr.k2i.adbeback.webapp.bean.CartBean;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.facade.MediaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class CheckoutController{

    @ModelAttribute("player")
    public Player player(){
        return new Player();
    }

    @RequestMapping("/checkout.html")
    public String checkout(Map<String, Object> model,HttpServletRequest request) throws Exception {
        CartBean cart = (CartBean) request.getSession().getAttribute(AdGameFacade.CART);
        if(cart.getNbProduct()==0){
            return "redirect:/cart.html";
        }
        return "checkout";
    }


}
