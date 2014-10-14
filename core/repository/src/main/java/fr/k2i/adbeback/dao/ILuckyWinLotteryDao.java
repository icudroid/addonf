package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLottery;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLotteryConfig;

/**
 * User: dimitri
 * Date: 14/10/14
 * Time: 16:02
 * Goal:
 */
public interface ILuckyWinLotteryDao extends IGenericDao<LuckyWinLottery, Long> {
    LuckyWinLottery findLuckyWinLotteryNotWin(LuckyWinLotteryConfig config);
}
