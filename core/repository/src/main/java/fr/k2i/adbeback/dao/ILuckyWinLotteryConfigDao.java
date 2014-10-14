package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLotteryConfig;

/**
 * User: dimitri
 * Date: 14/10/14
 * Time: 16:02
 * Goal:
 */
public interface ILuckyWinLotteryConfigDao  extends IGenericDao<LuckyWinLotteryConfig, Long> {
    LuckyWinLotteryConfig findByPrice(Double price);

    Long sizeOfLotteries(LuckyWinLotteryConfig config);
}
