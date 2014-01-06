package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.dao.jpa.GenreRepository;
import fr.k2i.adbeback.webapp.bean.CartBean;
import fr.k2i.adbeback.webapp.facade.MediaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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
public class HomeController {
    @Autowired
    private MediaFacade mediaFacade;

    @Autowired
    private GenreRepository genreRepository;

    @Value(value ="${addonf.static.url}" )
    private String staticUrl;


    @RequestMapping("/")
    public String home(Map<String, Object> model,HttpServletRequest request) throws Exception {
        CartBean cart = (CartBean) request.getSession().getAttribute("cart");
        if(cart==null){
            cart = new CartBean();
            request.getSession().setAttribute("cart",cart);
        }
        model.put("cart", cart);
        model.put("pushes", mediaFacade.getHomePush());
        model.put("bests", mediaFacade.getBestMusicDownload(null));
        model.put("categories",genreRepository.findAll());
        model.put("staticUrl",staticUrl);
        return "home";
    }


    @RequestMapping("/game")
    public String game(Map<String, Object> model) {
        return "game";
    }

    @RequestMapping("/logout-success")
    public String secure(Map<String, Object> model) {
        return "redirect:/";
    }
}
