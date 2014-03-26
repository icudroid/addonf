package fr.k2i.adbeback.service;

import java.util.List;
import java.util.Map;


import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.StatusGame;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.transaction.annotation.Transactional;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface AdGameManager extends GenericManager<AbstractAdGame, Long> {
	/**
	 * Generation du jeu$
     *
     *
     * @param selfAd
     * @param idPartner
     * @param idTransaction
     * @param idPlayer
     * @param gooseLevel   @return
     * @throws Exception
	 */
	AbstractAdGame generate(Boolean selfAd, String idPartner, String idTransaction, Long idPlayer, GooseLevel gooseLevel)throws Exception;
	

	/**
	 * 
	 * @param idAdGame
	 * @param score
	 * @param answers
     * @param statusGame
	 * @throws Exception
	 */
	void saveResponses(Long idAdGame, Integer score, Map<Integer, List<Long>> answers,StatusGame statusGame)throws Exception;


}
