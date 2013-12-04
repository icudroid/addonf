package fr.k2i.adbeback.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import fr.k2i.adbeback.core.business.goosegame.GooseCase_;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.goosegame.GooseCase;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;

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
@Repository("gooseCaseDao")
public class GooseCaseDao extends GenericDaoJpa<GooseCase, Long> implements fr.k2i.adbeback.dao.IGooseCaseDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public GooseCaseDao() {
        super(GooseCase.class);
    }

	public GooseCase getByNumber(Integer number,GooseLevel level) throws Exception {
        CriteriaBuilderHelper<GooseCase> helper = new CriteriaBuilderHelper(getEntityManager(),GooseCase.class);

        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(GooseCase_.number), number),
                helper.criteriaHelper.equal(helper.rootHelper.get(GooseCase_.level), level)
        );

        return helper.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<GooseCase> get(GooseLevel level, Integer start, Integer nb)
			throws Exception {
        List<Integer> nums = new ArrayList<Integer>();

        if(level.getEndCase().getNumber()>=nb){
            nb = level.getEndCase().getNumber();
        }

        for (int i = 0; i < nb; i++) {
            nums.add(start+i);
        }

        CriteriaBuilderHelper<GooseCase> helper = new CriteriaBuilderHelper(getEntityManager(),GooseCase.class);

        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(GooseCase_.level), level),
                helper.rootHelper.get(GooseCase_.number).in(nums)
        );

        helper.criteriaHelper.asc(helper.rootHelper.get(GooseCase_.number));

        return helper.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<GooseCase> getCases(GooseLevel level) throws Exception {

        CriteriaBuilderHelper<GooseCase> helper = new CriteriaBuilderHelper(getEntityManager(),GooseCase.class);

        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(GooseCase_.level), level)
        );

        helper.criteriaHelper.asc(helper.rootHelper.get(GooseCase_.number));

        return helper.getResultList();

	}

	
}

