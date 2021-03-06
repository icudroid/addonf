package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import fr.k2i.adbeback.core.business.goosegame.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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
@Repository("gooseLevelDao")
public class GooseLevelDao extends GenericDaoJpa<GooseLevel, Long> implements fr.k2i.adbeback.dao.IGooseLevelDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public GooseLevelDao() {
        super(GooseLevel.class);
    }


    @Override
    public List<? extends GooseLevel> findLevel(Integer level, Class<? extends GooseLevel> type) {

        QGooseLevel gooseLevel = QGooseLevel.gooseLevel;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(gooseLevel);


            BooleanExpression expression = gooseLevel.instanceOf(type);

            if(level!=null){
                expression.and(gooseLevel.level.eq(level));
            }

            query.where(expression);

        return query.select(gooseLevel).fetch();
    }


    @Override
    public LotteryGooseLevel findLotteryForNbAds(Integer nbAds) {
        QLotteryGooseLevel gooseLevel = QLotteryGooseLevel.lotteryGooseLevel;
        JPAQuery<LotteryGooseLevel> query = new JPAQuery(getEntityManager());
        query.from(gooseLevel)
                .where(
                        gooseLevel.minScore.eq(nbAds)
                );
        return query.select(gooseLevel).fetchOne();

    }

    @Override
    public SingleGooseLevel findForNbAds(Integer nbAds) {
        QSingleGooseLevel gooseLevel = QSingleGooseLevel.singleGooseLevel;
        JPAQuery<SingleGooseLevel> query = new JPAQuery(getEntityManager());
        query.from(gooseLevel)
                .where(
                       gooseLevel.minScore.eq(nbAds)
                );
        return query.select(gooseLevel).fetchOne();

    }

    @Override
    public DiceGooseLevel findDiceLevelForNbAds(Integer nbAds) {
        QDiceGooseLevel gooseLevel = QDiceGooseLevel.diceGooseLevel;
        JPAQuery<SingleGooseLevel> query = new JPAQuery(getEntityManager());
        query.from(gooseLevel)
                .where(
                        gooseLevel.maxScore.eq(nbAds)
                );
        return query.select(gooseLevel).fetchOne();

    }



    public void modifyLevelMinAmount(Long levelId, Integer minAmount) {
        Query q = getEntityManager().createQuery("UPDATE GooseLevel SET minValue=:minAmount where id =:id");
        q.setParameter("id", levelId);
        q.setParameter("minAmount", minAmount);
        q.executeUpdate();
    }
}

