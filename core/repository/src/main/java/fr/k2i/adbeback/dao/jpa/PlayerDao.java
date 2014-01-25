package fr.k2i.adbeback.dao.jpa;

import java.util.List;

import javax.persistence.Table;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.goosegame.GooseCase_;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel_;
import fr.k2i.adbeback.core.business.goosegame.GooseToken;
import fr.k2i.adbeback.core.business.goosegame.GooseToken_;
import fr.k2i.adbeback.core.business.media.QMedia;
import fr.k2i.adbeback.core.business.player.Player_;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.transaction.annotation.Transactional;

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
public class PlayerDao extends GenericDaoJpa<Player, Long> implements fr.k2i.adbeback.dao.IPlayerDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public PlayerDao() {
        super(Player.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Player> getPlayer() {
        CriteriaBuilderHelper<Player> helper = new CriteriaBuilderHelper(getEntityManager(),Player.class);
        helper.criteriaHelper.asc(helper.criteriaHelper.upper(helper.rootHelper.get(Player_.username)));
        return helper.getResultList();
    }


    @Transactional
    public Player savePlayer(Player player) {
        Player u = super.save(player);
        getEntityManager().flush();
        return u;
    }



	public Player loadUserByEmail(String email) {
        CriteriaBuilderHelper<Player> helper = new CriteriaBuilderHelper(getEntityManager(),Player.class);
        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(Player_.email), email)
        );
        List users = helper.getResultList();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return (Player) users.get(0);
        }
	}

    @Transactional
    @Override
    public GooseToken getPlayerGooseToken(Long idPlayer, Long idGooseLevel) {
        CriteriaBuilderHelper<GooseToken> helper = new CriteriaBuilderHelper(getEntityManager(),GooseToken.class);
        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.join(GooseToken_.player).get(Player_.id),idPlayer),
                helper.criteriaHelper.equal(helper.rootHelper.join(GooseToken_.gooseCase).join(GooseCase_.level).get(GooseLevel_.id), idGooseLevel)
        );

        return helper.getSingleResult();
    }

    @Override
    public Player findByEmailorUserName(String username) {
        QPlayer player = QPlayer.player;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(player)
                .where(
                        player.username.eq(username).or(player.email.eq(username))
                );

        return query.uniqueResult(player);
    }

    /**
     * {@inheritDoc}
     */
    public String getPlayerPassword(Long userId) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(Player.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where id=?", String.class, userId);
    }

    @Override
    public String getPlayerPassword(String username) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(Player.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where username=?", String.class, username);
    }

}

