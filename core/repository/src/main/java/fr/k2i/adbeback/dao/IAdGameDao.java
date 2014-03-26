package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IAdGameDao extends IGenericDao<AbstractAdGame, Long> {

    @Transactional
    boolean RuleIsUsed(AdService adRule);
}

