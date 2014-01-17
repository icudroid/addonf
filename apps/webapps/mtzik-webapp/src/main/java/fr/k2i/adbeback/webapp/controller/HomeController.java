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
import org.springframework.web.context.request.SessionScope;

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
public class HomeController{
    @Autowired
    private MediaFacade mediaFacade;

    @Autowired
    private GenreRepository genreRepository;


    @Value(value ="${addonf.bestdl.home.max:9}" )
    private Integer maxHomeBestDl;


    @RequestMapping("/")
    public String home(Map<String, Object> model,HttpServletRequest request) throws Exception {
        model.put("pushes", mediaFacade.getHomePush());
        model.put("bests", mediaFacade.getBestMusicDownload(null,maxHomeBestDl));
        model.put("categories",genreRepository.findAll());
        return "home";
    }

    @RequestMapping("/logout-success")
    public String secure(Map<String, Object> model) {
        return "redirect:/";
    }


    @RequestMapping(value = "/back")
    public String rateHandler(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
