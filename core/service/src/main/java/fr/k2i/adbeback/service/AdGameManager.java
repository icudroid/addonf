package fr.k2i.adbeback.service;

import java.util.Map;


import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.StatusGame;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface AdGameManager extends GenericManager<AbstractAdGame, Long> {
	/**
	 * Generation du jeu$
     *
     * @param idPlayer
	 * @param gooseLevel
     * @return
	 * @throws Exception
	 */
	AbstractAdGame generate(Long idPlayer, Long gooseLevel)throws Exception;
	

	/**
	 * 
	 * @param idAdGame
	 * @param score
	 * @param answers
     * @param statusGame
	 * @throws Exception
	 */
	void saveResponses(Long idAdGame, Integer score, Map<Integer, Long> answers,StatusGame statusGame)throws Exception;
	

}
