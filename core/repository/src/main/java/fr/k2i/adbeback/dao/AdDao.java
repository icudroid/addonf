package fr.k2i.adbeback.dao;

import java.util.Date;
import java.util.List;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.player.Player;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface AdDao extends GenericDao<Ad, Long> {

	/**
	 * Retourne toutes les publicité éligible
	 * @param date
	 * @return
	 * @throws Exception
	 */
	List<Ad> getAll(Date date)throws Exception;


    List<Ad> getAllValideFor(Player player);
}

