package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.player.AnonymPlayer;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.WebUser;
import fr.k2i.adbeback.dao.IPlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PlayerFacade {

    @Autowired
    private IPlayerDao playerDao;

    @Transactional
    public Player getCurrentPlayer() {
        Object principal = getAuthenticationPlayer().getPrincipal();
        if (!(principal instanceof WebUser)) {
            throw new AssertionError("Please check configuration. Should be Player in the principal.");
        }

        return playerDao.get(((WebUser) principal).getUser().getId());
    }

    protected Authentication getAuthenticationPlayer() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAnnonymous(){
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        if ((principal instanceof AnonymousAuthenticationToken)) {
            return true;
        }

        return false;
    }


    public boolean isAnnonymousPlayer() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        if ((principal instanceof WebUser)) {
            return playerDao.isAnonymPlayer(((WebUser) principal).getUser().getId());
        }
        return false;
    }
}
