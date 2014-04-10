package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.player.WebUser;
import fr.k2i.adbeback.core.business.user.QUser;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IWebUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Repository
public class WebUserDao extends GenericDaoJpa<User, Long> implements UserDetailsService,IWebUserDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public WebUserDao() {
        super(User.class);
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QUser user = QUser.user;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(user)
                .where(
                        user.email.eq(username).or(user.username.eq(username))
                );

        List<User> users = query.list(user);

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return new WebUser(users.get(0));
        }
    }

    @Override
    public UserDetails findByUsername(String username) {
        return loadUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String username) {
        QUser user = QUser.user;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(user)
                .where(
                        user.email.eq(username).or(user.username.eq(username))
                );

        List<User> users = query.list(user);

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return users.get(0);
        }
    }
}

