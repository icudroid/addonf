package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.transaction.Empreint;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.webapp.bean.configure.PaymentConfigure;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Autowired
    private ITransactionDao transactionDao;

    @RequestMapping(value = "/game",method = RequestMethod.POST)
    public String startGame(@ModelAttribute PaymentConfigure configure,Map<String, Object> model,HttpServletRequest request) throws Exception {
        model.put("game",adGameFacade.createMicroPurchaseAdGame(configure, request));
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



    @RequestMapping("/credit")
    public String credit(Map<String, Object> model,HttpServletRequest request) throws Exception {
        model.put("game", adGameFacade.createCreditAdGame(request));
        return "game";
    }


    @RequestMapping("/diceResult.html")
    public String diceResult(Map<String, Object> model,HttpServletRequest request) throws Exception {
        Long idBorrow = (Long) request.getSession().getAttribute(AdGameFacade.ID_BORROW);


        model.put("score",request.getSession().getAttribute(AdGameFacade.USER_SCORE));


        if(idBorrow==null){

            return "creditResult";
        }else{
            Empreint empreint = (Empreint) transactionDao.get(idBorrow);
            model.put("status", empreint.getStatus());


            model.put("idBorrow", idBorrow);
            return "diceResult";
        }



    }



}
