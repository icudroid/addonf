package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IViewedAdDao extends IGenericDao<ViewedAd, Long> {


    @Transactional
    ViewedAd findForToday(Player currentPlayer, AdService adService);
}

