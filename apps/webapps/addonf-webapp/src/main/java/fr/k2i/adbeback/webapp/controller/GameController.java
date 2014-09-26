package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.configure.PaymentConfigure;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public class GameController{

    @Autowired
    private AdGameFacade adGameFacade;

    @RequestMapping("/game")
    public String startGame(@ModelAttribute PaymentConfigure configure,Map<String, Object> model,HttpServletRequest request) throws Exception {
        model.put("game",adGameFacade.createAdGame(configure,request));
        return "game";
    }

    @RequestMapping("/resume")
    public String resume(Map<String, Object> model,HttpServletRequest request) {
        return "manage/gooseGame/partials/resume";
    }

    @RequestMapping("/repayBorrow/{idTr}")
    public String repayBorrow(@PathVariable Long idTr,Map<String, Object> model,HttpServletRequest request) throws Exception {
        model.put("game", adGameFacade.createBorrowGame(idTr,request));
        return "game";
    }


}
