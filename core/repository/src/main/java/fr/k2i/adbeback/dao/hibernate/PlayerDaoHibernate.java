package fr.k2i.adbeback.dao.hibernate;

import java.util.List;

import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.PlayerDao;

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
@Repository("playerDao")
public class PlayerDaoHibernate extends GenericDaoHibernate<Player, Long> implements PlayerDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public PlayerDaoHibernate() {
        super(Player.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Player> getPlayer() {
        Query qry =  getSession().createQuery("from User u order by upper(u.username)");
        return qry.list();
    }


    public Player savePlayer(Player player) {
        Player u = super.save(player);
        getEntityManager().flush();
        return u;
    }



	public Player loadUserByEmail(String email) {
        List users = getSession().createCriteria(Player.class).add(Restrictions.eq("email", email)).list();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return (Player) users.get(0);
        }
	}



    /**
     * {@inheritDoc}
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getSession().createCriteria(Player.class).add(Restrictions.eq("username", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getPlayerPassword(Long userId) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(PlayerDao.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where id=?", String.class, userId);
    }

    @Override
    public String getPlayerPassword(String username) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(PlayerDao.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where username=?", String.class, username);
    }

}

