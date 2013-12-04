package fr.k2i.adbeback.dao;

import java.util.List;

import fr.k2i.adbeback.core.business.goosegame.GooseWin;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IGooseWinDao extends IGenericDao<GooseWin, Long> {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<GooseWin> getLastWinners()throws Exception;

}

