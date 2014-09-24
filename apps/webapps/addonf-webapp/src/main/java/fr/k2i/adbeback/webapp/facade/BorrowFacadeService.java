package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.transaction.Empreint;
import fr.k2i.adbeback.core.business.transaction.TransactionHistory;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.webapp.bean.EmpreintSmallBean;
import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
public class BorrowFacadeService {

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private ITransactionDao transactionDao;


    @Transactional
    public List<EmpreintSmallBean> getBorrows(){

        Player player = playerFacade.getCurrentPlayer();

        List<Empreint> empreints = transactionDao.getActiveBorrows(player);

        List<EmpreintSmallBean> res = new ArrayList<EmpreintSmallBean>();

        for (Empreint empreint : empreints) {

            EmpreintSmallBean build = EmpreintSmallBean.builder()
                    .id(empreint.getId())
                    .adAmount(empreint.getAdAmount())
                    .adAmountLeft(empreint.getAdAmountLeft())
                    .endDate(empreint.getEndDate())
                    .startDate(empreint.getStartDate())
                    .status(empreint.getStatus())
                    .order(empreint.getOrder().getProduct())
                    .histories(empreint.getHistories())
                    .build();


            res.add(build);
        }

        return res;



    }

    public Page<HistoryAdGameBean> getHistoriesBorrowGame(Long tr, PageRequest pageRequest) {
        Player player = playerFacade.getCurrentPlayer();
        List<HistoryAdGameBean> list = new ArrayList<HistoryAdGameBean>();

        boolean  ok = transactionDao.isTransactionOkForPlayer(tr,player);
        if(!ok) throw new SecurityException();


        List<AdGame> historiesBorrowGame = transactionDao.getHistoriesBorrowGame(player, tr,pageRequest);

        for (AdGame adGame : historiesBorrowGame) {
            list.add(HistoryAdGameBean.builder().adAmount(adGame.getScore().getScore()).generated(adGame.getGenerated()).build());
        }

        return new PageImpl<HistoryAdGameBean>(list, pageRequest,transactionDao.countHistoryGame(tr));
    }
}
