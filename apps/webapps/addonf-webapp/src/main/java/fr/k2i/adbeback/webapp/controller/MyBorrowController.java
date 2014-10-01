package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.transaction.TransactionHistory;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.exception.LimitBorrowException;
import fr.k2i.adbeback.service.BorrowManager;
import fr.k2i.adbeback.webapp.bean.EmpreintSmallBean;
import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import fr.k2i.adbeback.webapp.facade.BorrowFacadeService;
import fr.k2i.adbeback.webapp.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:51
 * Goal:
 */
@Controller
public class MyBorrowController {


    @Autowired
    private BorrowFacadeService borrowFacadeService;






    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyBorrowController.Path.MY_BORROW)
    public String show(ModelMap modelMap,@RequestParam(required = false,value = "idBorrow")Long idBorrow){
        List<EmpreintSmallBean> borrows = null;
        if(idBorrow == null){
            borrows = borrowFacadeService.getBorrows();
        }else{
            borrows = new ArrayList<>();
            borrows.add(borrowFacadeService.getBorrow(idBorrow));
        }

        switch (borrows.size()){
            case 0 :
                return IMetaDataController.MyBorrowController.View.NO_BORROWS;
            case 1 :
                modelMap.addAttribute("borrow",borrows.get(0));
                return IMetaDataController.MyBorrowController.View.BORROW;
            default:
                modelMap.addAttribute("borrows",borrows);
                return IMetaDataController.MyBorrowController.View.LIST_BORROWS;
        }
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.MyBorrowController.Path.HISTORY_BORROW_GAME)
    @ResponseBody
    public Page<HistoryAdGameBean> historiesBorrowGame(@PathVariable("tr") Long tr,Pageable pageable){
        return borrowFacadeService.getHistoriesBorrowGame(tr,pageable);
    }



/*    @Secured(value = "ROLE_USER")
    @RequestMapping(value = "/cet")
    @ResponseBody
    public String createEmpreinTest() throws LimitBorrowException {
        borrowFacadeService.cet();
        return "ok";
    }*/


}
