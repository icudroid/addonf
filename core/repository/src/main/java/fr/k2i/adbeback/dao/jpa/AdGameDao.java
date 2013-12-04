package fr.k2i.adbeback.dao.jpa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.player.Player_;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
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
@Repository("adGameDao")
public class AdGameDao extends GenericDaoJpa<AbstractAdGame, Long> implements fr.k2i.adbeback.dao.IAdGameDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdGameDao() {
        super(AbstractAdGame.class);
    }

	@SuppressWarnings("unchecked")
	public List<AbstractAdGame> findWonAdGame(Long idPlayer) throws Exception {

        CriteriaBuilderHelper<AbstractAdGame> helper = new CriteriaBuilderHelper(getEntityManager(),AbstractAdGame.class);

        Calendar now = new GregorianCalendar();
        now.add(Calendar.DATE, -7);

        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.join(AbstractAdGame_.player).get(Player_.id), idPlayer),
                helper.criteriaHelper.equal(helper.rootHelper.get(AbstractAdGame_.statusGame), StatusGame.Win),
                helper.criteriaHelper.greaterThan(helper.rootHelper.get(AbstractAdGame_.generated), now.getTime())
        );

        helper.criteriaHelper.desc(helper.rootHelper.get(AbstractAdGame_.generated));

		return helper.getResultList();
	}


}

