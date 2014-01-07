package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.goosegame.QMultiGooseLevel;
import fr.k2i.adbeback.core.business.goosegame.QSingleGooseLevel;
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

        if(multiple){
            QMultiGooseLevel multiGooseLevel = QMultiGooseLevel.multiGooseLevel;
            JPAQuery query = new JPAQuery(getEntityManager());
            query.from(multiGooseLevel)
                    .where(
                            multiGooseLevel.level.eq(level)
                    );
            return query.list(multiGooseLevel);
        }else{
            QSingleGooseLevel singleGooseLevel = QSingleGooseLevel.singleGooseLevel;
            JPAQuery query = new JPAQuery(getEntityManager());
            query.from(singleGooseLevel)
                    .where(
                            singleGooseLevel.level.eq(level)
                    );
            return query.list(singleGooseLevel);
        }
    }

    @Override
    public Long findForNbAds(Integer nbAds) {
        QSingleGooseLevel gooseLevel = QSingleGooseLevel.singleGooseLevel;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(gooseLevel)
                .where(
                       gooseLevel.minScore.eq(nbAds)
                );
        return query.singleResult(gooseLevel.id);

    }

    public void modifyLevelMinAmount(Long levelId, Integer minAmount) {
        Query q = getEntityManager().createQuery("UPDATE GooseLevel SET minValue=:minAmount where id =:id");
        q.setParameter("id", levelId);
        q.setParameter("minAmount", minAmount);
        q.executeUpdate();
    }
}

