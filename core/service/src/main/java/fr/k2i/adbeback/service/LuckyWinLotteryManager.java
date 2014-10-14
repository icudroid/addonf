package fr.k2i.adbeback.service;

import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLottery;

/**
 * User: dimitri
 * Date: 14/10/14
 * Time: 16:04
 * Goal:
 */
public interface LuckyWinLotteryManager extends GenericManager<LuckyWinLottery, Long> {
    public boolean doLottery(Double price,AdGameTransaction transaction);
}
