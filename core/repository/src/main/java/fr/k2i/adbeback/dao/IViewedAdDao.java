package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.media.Artist;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.media.Productor;
import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IViewedAdDao extends IGenericDao<ViewedAd, Long> {


    @Transactional
    ViewedAd findForToday(Player currentPlayer, AdService adService);
}

