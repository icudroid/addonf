package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.AdGameMedia;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.dao.jpa.GenreRepository;
import fr.k2i.adbeback.webapp.bean.CartBean;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.facade.MediaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static fr.k2i.adbeback.webapp.facade.AdGameFacade.ID_ADGAME;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 29/10/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class GameController extends AbstractController{

    @Autowired
    private AdGameFacade adGameFacade;

    @RequestMapping("/game")
    public String game(Map<String, Object> model,HttpServletRequest request) {
        return "game";
    }



    @RequestMapping("/downloadMusics.html")
    public String downloadMusics(Map<String, Object> model,HttpServletRequest request) {
        Long idGame = (Long) request.getSession().getAttribute(ID_ADGAME);

        model.put("filename",adGameFacade.getFilename(idGame));
        return "cartDownload";
    }
}
