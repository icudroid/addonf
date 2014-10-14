package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.*;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLottery;
import fr.k2i.adbeback.core.business.game.lottery.LuckyWinLotteryConfig;
import fr.k2i.adbeback.core.business.game.lottery.QLuckyWinLottery;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.QBidCategoryMedia;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class LuckyWinLotteryDao extends GenericDaoJpa<LuckyWinLottery, Long> implements fr.k2i.adbeback.dao.ILuckyWinLotteryDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public LuckyWinLotteryDao() {
        super(LuckyWinLottery.class);
    }


    @Override
    public LuckyWinLottery findLuckyWinLotteryNotWin(LuckyWinLotteryConfig config) {
        JPAQuery query = new JPAQuery(getEntityManager());
        QLuckyWinLottery qLuckyWinLottery = QLuckyWinLottery.luckyWinLottery;

        query.from(qLuckyWinLottery).where(qLuckyWinLottery.winLottery.isNull());

        return query.uniqueResult(qLuckyWinLottery);
    }
}

