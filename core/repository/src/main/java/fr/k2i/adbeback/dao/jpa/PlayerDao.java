package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import fr.k2i.adbeback.core.business.goosegame.GooseToken;
import fr.k2i.adbeback.core.business.goosegame.QGooseToken;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
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
        JPAQuery query = new JPAQuery(getEntityManager());
        QPlayer player = QPlayer.player;
        query.from(player).orderBy(player.username.upper().asc());
        return query.list(player);
    }


    @Transactional
    public Player savePlayer(Player player) {
        Player u = super.save(player);
        getEntityManager().flush();
        return u;
    }



	public Player loadUserByEmail(String email) {
        JPAQuery query = new JPAQuery(getEntityManager());
        QPlayer player = QPlayer.player;
        query.from(player).where(player.email.eq(email));

        List<Player> users = query.list(player);
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
	}

    @Transactional
    @Override
    public GooseToken getPlayerGooseToken(Long idPlayer, Long idGooseLevel) {

        JPAQuery query = new JPAQuery(getEntityManager());
        QGooseToken gooseToken = QGooseToken.gooseToken;
        query.from(gooseToken).where(gooseToken.player.id.eq(idPlayer),gooseToken.gooseCase.level.id.eq(idGooseLevel));

        return query.uniqueResult(gooseToken);
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

    @Override
    public void enable(Long userId) {

        log.info("user with id "+ userId +" enabled");

        QPlayer qPlayer = QPlayer.player;
        JPAUpdateClause query = new JPAUpdateClause(getEntityManager(),qPlayer);
        query.where(qPlayer.id.eq(userId))
                .set(qPlayer.enabled,true);

        query.execute();
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

