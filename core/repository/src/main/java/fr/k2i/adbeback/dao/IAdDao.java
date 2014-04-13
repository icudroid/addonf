package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.VideoAd;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.user.MediaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IAdDao extends IGenericDao<Ad, Long> {

	/**
	 * Retourne toutes les publicité éligible
     * @param player
	 * @return
	 * @throws Exception
	 */
    @Transactional
    List<Ad> getAllValidFor(Player player);

    @Transactional
    void updateAmountForAd(AdService adRule);

    @Transactional
    List<Ad> findByBrand(Brand currentUser);

    @Transactional
    Page<Ad> findByBrand(Brand brand,Pageable pageable);

    @Transactional
    List<VideoAd> findNoEncodedAd();

    @Transactional
    List<Ad> getAllValidForAndProvidedBy(Player player, MediaUser media);

    List<Ad> findByBrands(List<Brand> brands);

    Page<Ad> findByBrands(List<Brand> brands, Pageable pageable);
}

