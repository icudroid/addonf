package fr.k2i.adbeback.service;

import java.util.List;

import fr.k2i.adbeback.dao.IPlayerDao;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.transaction.annotation.Transactional;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface PlayerManager extends GenericManager<Player, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setPlayerDao(IPlayerDao playerDao);

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    Player getPlayer(String playerId);


    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<Player> getPlayers();

    /**
     * Saves a user's information.
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    Player savePlayer(Player player) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    void removePlayer(String playerId);

    /**
     * 
     * @param email
     * @return
     */
    Player getPlayerByEmail(String email);

    @Transactional
    void changePasswd(Player user, String newPwd);
}
