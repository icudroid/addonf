package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLotteryConfig;
import fr.k2i.adbeback.core.business.game.lottery.QLuckyWinLottery;
import fr.k2i.adbeback.core.business.game.lottery.QLuckyWinLotteryConfig;
import org.springframework.stereotype.Repository;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository
public class LuckyWinLotteryConfigDao extends GenericDaoJpa<LuckyWinLotteryConfig, Long> implements fr.k2i.adbeback.dao.ILuckyWinLotteryConfigDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public LuckyWinLotteryConfigDao() {
        super(LuckyWinLotteryConfig.class);
    }


    @Override
    public LuckyWinLotteryConfig findByPrice(Double price) {
        JPAQuery<LuckyWinLotteryConfig> query = new JPAQuery(getEntityManager());

        QLuckyWinLotteryConfig qLuckyWinLotteryConfig =   QLuckyWinLotteryConfig.luckyWinLotteryConfig;
        query.from(qLuckyWinLotteryConfig)
            .where(qLuckyWinLotteryConfig.minPrice.goe(price).and(qLuckyWinLotteryConfig.maxPrice.loe(price)));
        return query.select(qLuckyWinLotteryConfig).fetchOne();
    }

    @Override
    public Long sizeOfLotteries(LuckyWinLotteryConfig config) {
        JPAQuery query = new JPAQuery(getEntityManager());
        QLuckyWinLotteryConfig qLuckyWinLotteryConfig =   QLuckyWinLotteryConfig.luckyWinLotteryConfig;
        QLuckyWinLottery qLuckyWinLottery = QLuckyWinLottery.luckyWinLottery;

        query.from(qLuckyWinLotteryConfig).join(qLuckyWinLotteryConfig.lotteries,qLuckyWinLottery)
                .where(qLuckyWinLotteryConfig.eq(config));

        return (Long) query.select(qLuckyWinLottery.count()).fetchOne();

    }
}

