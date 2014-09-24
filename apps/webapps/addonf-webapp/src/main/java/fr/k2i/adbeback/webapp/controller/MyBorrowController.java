package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.transaction.TransactionHistory;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.webapp.bean.EmpreintSmallBean;
import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import fr.k2i.adbeback.webapp.facade.BorrowFacadeService;
import fr.k2i.adbeback.webapp.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = IMetaDataController.Path.MY_BORROW)
    public String show(ModelMap modelMap){

        List<EmpreintSmallBean> borrows = borrowFacadeService.getBorrows();
        switch (borrows.size()){
            case 0 :
                return IMetaDataController.View.MyBorrowController.NO_BORROWS;
            case 1 :
                modelMap.addAttribute("borrow",borrows.get(0));
                return IMetaDataController.View.MyBorrowController.BORROW;
            default:
                modelMap.addAttribute("borrows",borrows);
                return IMetaDataController.View.MyBorrowController.LIST_BORROWS;
        }
    }


    @Secured(value = "ROLE_USER")
    @RequestMapping(value = IMetaDataController.Path.HISTORY_BORROW_GAME)
    @ResponseBody
    public Page<HistoryAdGameBean> historiesBorrowGame(@PathVariable Long tr,PageRequest pageRequest){
        return borrowFacadeService.getHistoriesBorrowGame(tr,pageRequest);
    }

}
