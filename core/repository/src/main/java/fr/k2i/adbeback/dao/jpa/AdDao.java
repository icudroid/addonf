package fr.k2i.adbeback.dao.jpa;

import java.util.List;

import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.ad.Ad;

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
public class AdDao extends GenericDaoJpa<Ad, Long> implements fr.k2i.adbeback.dao.IAdDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdDao() {
        super(Ad.class);
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

