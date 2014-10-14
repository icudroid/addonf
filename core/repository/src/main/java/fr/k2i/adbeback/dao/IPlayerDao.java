package fr.k2i.adbeback.dao;

import java.util.List;

import fr.k2i.adbeback.core.business.goosegame.GooseToken;
import fr.k2i.adbeback.core.business.user.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.k2i.adbeback.core.business.player.Player;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IPlayerDao extends IGenericDao<Player, Long> {

    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    List<Player> getPlayer();

    /**
     * Saves a user's information.
     * @param player the object to be saved
     * @return the persisted User object
     */
    Player savePlayer(Player player);

    /**
     * Retrieves the password in DB for a user
     * @param username the user's username
     * @return the password in DB, if the user is already persisted
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    String getPlayerPassword(String username);

    /**
     * 
     * @param email
     * @return
     */
	Player loadUserByEmail(String email);

    GooseToken getPlayerGooseToken(Long idPlayer, Long idGooseLevel);

    Player findByEmailorUserName(String username);

    void enable(Long userId);

    boolean isAnonymPlayer(Long id);
}

