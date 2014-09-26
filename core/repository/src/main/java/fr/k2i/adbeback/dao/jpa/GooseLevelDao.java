package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
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
    public List<? extends GooseLevel> findLevel(Integer level, Boolean multiple) {

        QGooseLevel gooseLevel = QGooseLevel.gooseLevel;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(gooseLevel);


            BooleanExpression expression = null;

            if(multiple){
                expression = gooseLevel.instanceOf(MultiGooseLevel.class);
            }else{
                expression = gooseLevel.instanceOf(SingleGooseLevel.class);
            }

            if(level!=null){
                expression.and(gooseLevel.level.eq(level));
            }

            query.where(expression);

        return query.list(gooseLevel);
    }

    @Override
    public SingleGooseLevel findForNbAds(Integer nbAds) {
        QSingleGooseLevel gooseLevel = QSingleGooseLevel.singleGooseLevel;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(gooseLevel)
                .where(
                       gooseLevel.minScore.eq(nbAds)
                );
        return query.singleResult(gooseLevel);

    }

    @Override
    public DiceGooseLevel findDiceLevelForNbAds(Integer nbAds) {
        QDiceGooseLevel gooseLevel = QDiceGooseLevel.diceGooseLevel;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(gooseLevel)
                .where(
                        gooseLevel.maxScore.eq(nbAds)
                );
        return query.singleResult(gooseLevel);

    }

    public void modifyLevelMinAmount(Long levelId, Integer minAmount) {
        Query q = getEntityManager().createQuery("UPDATE GooseLevel SET minValue=:minAmount where id =:id");
        q.setParameter("id", levelId);
        q.setParameter("minAmount", minAmount);
        q.executeUpdate();
    }
}

