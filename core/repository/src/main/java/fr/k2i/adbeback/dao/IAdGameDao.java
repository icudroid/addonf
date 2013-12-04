package fr.k2i.adbeback.dao;

import java.util.List;

import fr.k2i.adbeback.core.business.game.AbstractAdGame;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IAdGameDao extends IGenericDao<AbstractAdGame, Long> {
	/**
	 * 
	 * @param idPlayLong
	 * @return
	 * @throws Exception
	 */
	List<AbstractAdGame> findWonAdGame(Long idPlayLong)throws Exception;
}

