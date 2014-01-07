package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.goosegame.GooseLevel;

import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IGooseLevelDao extends IGenericDao<GooseLevel, Long> {


    List<? extends GooseLevel> findLevel(Integer level, Boolean multiple);

    Long findForNbAds(Integer nbAds);
}

