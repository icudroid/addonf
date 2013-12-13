package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.goosegame.GooseLevel_;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.goosegame.GooseLevel;

import javax.persistence.Query;
import javax.persistence.Table;
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
    public List<GooseLevel> findLevel(Integer level, Boolean multiple) {
        CriteriaBuilderHelper<GooseLevel> helper = new CriteriaBuilderHelper(getEntityManager(),GooseLevel.class);


        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(GooseLevel_.multiple), multiple)
        );


        if(level!=null){
            helper.criteriaHelper.and(helper.criteriaHelper.equal(helper.rootHelper.get(GooseLevel_.level), level));
        }

        return helper.getResultList();
    }

    public void modifyLevelMinAmount(Long levelId, Integer minAmount) {
        Query q = getEntityManager().createQuery("UPDATE GooseLevel SET minValue=:minAmount where id =:id");
        q.setParameter("id", levelId);
        q.setParameter("minAmount", minAmount);
        q.executeUpdate();
    }
}

