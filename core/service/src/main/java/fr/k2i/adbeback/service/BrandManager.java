package fr.k2i.adbeback.service;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IPlayerDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface BrandManager extends GenericManager<Brand, Long> {

    @Transactional
    void changePasswd(String username, String password);

    @Transactional
    void changePasswd(User user, String newPwd);
}
