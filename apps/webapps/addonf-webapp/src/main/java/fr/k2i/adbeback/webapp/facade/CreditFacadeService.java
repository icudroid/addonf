package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:55
 * Goal:
 */
@Service
public class CreditFacadeService {

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private ITransactionDao transactionDao;


    public Page<HistoryAdGameBean> getCreditGame(Pageable pageRequest) {
        Player player = playerFacade.getCurrentPlayer();
        List<HistoryAdGameBean> list = new ArrayList<HistoryAdGameBean>();


        List<AdGame> historiesCreditGame = transactionDao.getHistoriesCreditGame(player, pageRequest);

        for (AdGame adGame : historiesCreditGame) {
            list.add(HistoryAdGameBean.builder().adAmount(adGame.getScore().getScore()).generated(adGame.getGenerated()).build());
        }

        return new PageImpl<HistoryAdGameBean>(list, pageRequest,transactionDao.countHistoryCreditGame(playerFacade.getCurrentPlayer().getId()));
    }


    @Transactional
    public Long getAdAmount() {
        return playerFacade.getCurrentPlayer().getWallet().getAdAmount();
    }
}
