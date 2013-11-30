package fr.k2i.adbeback.dao.hibernate;

import java.util.Date;
import java.util.List;

import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdRule_;
import fr.k2i.adbeback.core.business.ad.rule.DateRule_;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Ad_;
import fr.k2i.adbeback.dao.AdDao;

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
@Repository("adDao")
public class AdDaoHibernate extends GenericDaoHibernate<Ad, Long> implements AdDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdDaoHibernate() {
        super(Ad.class);
    }

	public List<Ad> getAll(Date date) throws Exception {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ad.class);
		Criteria createAlias = criteria.createAlias("rules", "rule");
		createAlias.add(Restrictions.and(Restrictions.le("rule.startDate", date), Restrictions.gt("rule.endDate", date)));
		return criteria.list();
	}

    @Override
    public List<Ad> getAllValideFor(Player player) {
        Address address = player.getAddress();

        /*
            Les règles sont les suivantes :
                - Si l'annonceur a mit des critères par publicités (Age, Sex, Ville , Pays)
                - Une publicité ne doit pas être vu plus de X fois configurable par l'annonceur

         */


        LocalDate date = new LocalDate();
        
        Sex sex = player.getSex();
        
        LocalDate birthday = new LocalDate(player.getBirthday());
        Years years = Years.yearsBetween(birthday, date);
        
        Integer age = years.getYears();


        CriteriaBuilderHelper<Ad> helper = new CriteriaBuilderHelper(getEntityManager(),Ad.class);
/*        helper.criteriaHelper.and(
                helper.criteriaHelper.lessThan(helper.rootHelper.join(Ad_.rules).get(DateRule_.startDate),date.toDate()),
                helper.criteriaHelper.greaterThan(helper.rootHelper.join(Ad_.rules).get(DateRule_.endDate),date.toDate())
        );*/
       return helper.getResultList();
    }


}

