package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.core.business.player.WebPlayer;
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
public class WebPlayerDao extends GenericDaoJpa<Player, Long> implements UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public WebPlayerDao() {
        super(Player.class);
    }


    /**
     * {@inheritDoc}
     */
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QPlayer user = QPlayer.player;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(user)
                .where(
                        user.username.eq(username).or(user.email.eq(username))
                );

        List<Player> users = query.list(user);

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return new WebPlayer(users.get(0));
        }
    }

}

