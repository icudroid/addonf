package fr.k2i.adbeback.dao.jpa;

import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.goosegame.QGooseWin;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.goosegame.GooseWin;

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
@Repository("gooseWinDao")
public class GooseWinDao extends GenericDaoJpa<GooseWin, Long> implements fr.k2i.adbeback.dao.IGooseWinDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public GooseWinDao() {
        super(GooseWin.class);
    }

	public List<GooseWin> getLastWinners() throws Exception {
        JPAQuery query = new JPAQuery(getEntityManager());
        QGooseWin gooseWin =  QGooseWin.gooseWin;
        query.from(gooseWin).orderBy(gooseWin.windate.desc()).limit(25);

        return query.select(gooseWin).fetch();
	}

}

