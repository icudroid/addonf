package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.player.WebUser;
import fr.k2i.adbeback.core.business.user.Admin;
import fr.k2i.adbeback.core.business.user.QAdmin;
import fr.k2i.adbeback.core.business.user.QUser;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IWebUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        List<User> users = query.select(user).fetch();

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
        QAdmin user = QAdmin.admin;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(user)
                .where(
                        user.email.eq(username).or(user.username.eq(username))
                );

        List<Admin> users = query.select(user).fetch();

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return users.get(0);
        }
    }

    @Override
    public void enable(Long idUser) {
        get(idUser).setEnabled(true);
    }


    @Transactional
    @Override
    public void setPassword(Long idUser, String password) {
        get(idUser).setPassword(passwordEncoder.encodePassword(password,null));
    }
}

