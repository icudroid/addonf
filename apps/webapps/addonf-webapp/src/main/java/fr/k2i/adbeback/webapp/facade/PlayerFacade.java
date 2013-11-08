package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PlayerFacade {

    public Player getCurrentPlayer() {
        Object principal = getAuthenticationPlayer().getPrincipal();
        if (!(principal instanceof Player)) {
            throw new AssertionError("Please check configuration. Should be Player in the principal.");
        }

        return (Player) principal;
    }

    protected Authentication getAuthenticationPlayer() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
