package fr.k2i.adbeback.dao;

import java.util.List;

import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.player.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
	List<AbstractAdGame> findWonAdGame(Long idPlayLong)throws Exception;

    @Transactional
    Page<Media> getDownloadedMusic(Player currentPlayer, long genreId, String req, Pageable pageable);

    @Transactional
    Boolean musicIsWonByPlayer(Player player, Long musicId);

    @Transactional
    boolean isGeneratedWithRule(AdService adRule);
}

