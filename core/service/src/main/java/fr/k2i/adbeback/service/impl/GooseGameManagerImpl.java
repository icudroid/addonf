package fr.k2i.adbeback.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.k2i.adbeback.core.business.goosegame.GooseCase;
import fr.k2i.adbeback.core.business.goosegame.GooseGame;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.goosegame.GooseWin;
import fr.k2i.adbeback.dao.GooseCaseDao;
import fr.k2i.adbeback.dao.GooseGameDao;
import fr.k2i.adbeback.dao.GooseLevelDao;
import fr.k2i.adbeback.dao.GooseTokenDao;
import fr.k2i.adbeback.dao.GooseWinDao;
import fr.k2i.adbeback.service.GooseGameManager;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("gooseGameManager")
public class GooseGameManagerImpl extends GenericManagerImpl<GooseGame, Long> implements GooseGameManager {

	private GooseCaseDao gooseCaseDao;
	private GooseTokenDao gooseTokenDao;
	private GooseGameDao gooseGameDao;
	private GooseLevelDao gooseLevelDao;
	private GooseWinDao gooseWinDao;
	
	@Autowired
	public void setGooseCaseDao(GooseCaseDao gooseCaseDao) {
		this.gooseCaseDao = gooseCaseDao;
	}
	@Autowired
	public void setGooseTokenDao(GooseTokenDao gooseTokenDao) {
		this.gooseTokenDao = gooseTokenDao;
	}
	@Autowired
	public void setGooseGameDao(GooseGameDao gooseGameDao) {
		this.gooseGameDao = gooseGameDao;
	}
	@Autowired
	public void setGooseLevelDao(GooseLevelDao gooseLevelDao) {
		this.gooseLevelDao = gooseLevelDao;
	}
	@Autowired
	public void setGooseWinDao(GooseWinDao gooseWinDao) {
		this.gooseWinDao = gooseWinDao;
	}


	public GooseCase getCase(long id) throws Exception {
		return gooseCaseDao.get(id);
	}

	public List<GooseCase> getCases(GooseLevel level, Integer startNumber,
			int nb) throws Exception {
		return gooseCaseDao.get(level, startNumber, nb);
	}

	public GooseLevel getNextLevel(GooseLevel level) throws Exception {
		return gooseGameDao.getNextLevel(level);
	}

    @Transactional
	public GooseCase getCaseByNumber(Integer num, GooseLevel level)
			throws Exception {
		return gooseCaseDao.getByNumber(num, level);
	}

	public void addToLevel(GooseLevel level, Double value) throws Exception {
		gooseGameDao.addToLevel(level, value);
	}

	public void resetLevelValue(GooseLevel level) throws Exception {
		gooseGameDao.resetLevelValue(level);
	}

	public List<GooseCase> getLevelCases(GooseLevel level) throws Exception {
		return gooseCaseDao.getCases(level);
	}

	public List<GooseWin> getLastWinners() throws Exception {
		return gooseWinDao.getLastWinners();
	}

}
