package fr.k2i.adbeback.service.impl;

import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import fr.k2i.adbeback.core.business.game.StatusGame;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLottery;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLotteryConfig;
import fr.k2i.adbeback.dao.ILuckyWinLotteryConfigDao;
import fr.k2i.adbeback.dao.ILuckyWinLotteryDao;
import fr.k2i.adbeback.service.LuckyWinLotteryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service
public class LuckyWinLotteryManagerImpl extends GenericManagerImpl<LuckyWinLottery, Long>
		implements LuckyWinLotteryManager {

    @Autowired
    private ILuckyWinLotteryConfigDao luckyWinLotteryConfigDao;

    @Autowired
    private ILuckyWinLotteryDao luckyWinLotteryDao;


    @Transactional
    @Override
    public boolean doLottery(Double price, AdGameTransaction transaction) {

        if(transaction.getStatusGame().equals(StatusGame.Win)){
            LuckyWinLotteryConfig config = luckyWinLotteryConfigDao.findByPrice(price);
            Integer lucky = config.getLucky();

            LuckyWinLottery luckyWinLottery = luckyWinLotteryDao.findLuckyWinLotteryNotWin(config);
            // il n'y pas de de lottery alors il faut la créer
            if(luckyWinLottery==null){
                luckyWinLottery = luckyWinLotteryDao.save(new LuckyWinLottery());
                config.addLuckyWinLottery(luckyWinLottery);
            }

            Long nbLottery = luckyWinLotteryConfigDao.sizeOfLotteries(config);
            if(lucky == nbLottery.intValue()){
                //on fait gagner
                luckyWinLottery.setWinLottery(transaction);
                return true;
            }else{
                //perdu
                luckyWinLottery.addLostLottery(transaction);
                return false;
            }

        }

        return false;
    }
}
