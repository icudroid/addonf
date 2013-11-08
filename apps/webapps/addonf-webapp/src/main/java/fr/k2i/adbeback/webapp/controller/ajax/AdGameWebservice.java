package fr.k2i.adbeback.webapp.controller.ajax;

import fr.k2i.adbeback.bean.AdBean;
import fr.k2i.adbeback.bean.AdGameBean;
import fr.k2i.adbeback.bean.PossibilityBean;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class AdGameWebservice {

    @Autowired
    private AdGameFacade adGameFacade;

    @Autowired
    private PlayerFacade playerFacade;

    @RequestMapping(value = "/rest/createGame", method = RequestMethod.GET)
    public @ResponseBody AdGameBean createGame(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return adGameFacade.createAdGame(playerFacade.getCurrentPlayer().getId(), request);
    }
}
