package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

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
@Repository("gooseGameDao")
public class GooseGameDao extends GenericDaoJpa<GooseGame, Long> implements fr.k2i.adbeback.dao.IGooseGameDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public GooseGameDao() {
        super(GooseGame.class);
    }

	public void addToLevel(GooseLevel level, Double value) throws Exception {
        Query q = getEntityManager().createQuery("UPDATE GooseLevel SET value=value+:v where id =:id");
        q.setParameter("v", value);
        q.setParameter("id", level.getId());
        q.executeUpdate();
	}

	public void resetLevelValue(GooseLevel level) throws Exception {
        Query q = getEntityManager().createQuery("UPDATE GooseLevel SET value=0 where id =:id");
        q.setParameter("id", level.getId());
        q.executeUpdate();
	}

	public GooseLevel getNextLevel(GooseLevel level) throws Exception {

        CriteriaBuilderHelper<GooseLevel> helper = new CriteriaBuilderHelper(getEntityManager(),GooseLevel.class);

        Integer l= 0;
        if(level!=null){
            l = level.getLevel()+1;
        }

        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(GooseLevel_.level), l)
        );

        return helper.getSingleResult();
	}


}

