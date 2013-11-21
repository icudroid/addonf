package fr.k2i.adbeback.service;

import java.util.Map;


import fr.k2i.adbeback.core.business.game.AdGame;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface AdGameManager extends GenericManager<AdGame, Long> {
	/**
	 * Generation du jeu$
     *
     * @param idPlayer
	 * @param gooseLevel
     * @return
	 * @throws Exception
	 */
	AdGame generate(Long idPlayer, Long gooseLevel)throws Exception;
	

	/**
	 * 
	 * @param idAdGame
	 * @param score
	 * @param answers
	 * @throws Exception
	 */
	void saveResponses(Long idAdGame, Integer score, Map<Integer, Long> answers)throws Exception;
	

}
