package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.dao.jpa.GooseLevelDao;
import fr.k2i.adbeback.service.GooseGameManager;
import fr.k2i.adbeback.webapp.bean.GooseLevelGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 06/12/13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GooseGameFacade {
    @Autowired
    private GooseGameManager gooseGameManager;

    @Autowired
    private GooseLevelDao gooseLevelDao;


    @Transactional
    public GooseLevel generateSingleLevel(Integer nbCase,Long numLevel){
        GooseLevel level = new GooseLevel();
        level.setLevel(numLevel);
        level.setMinValue(0);
        level.setMultiple(false);
        level.setNbMaxAdByPlay(nbCase);
        level.setStrong(null);
        level.setValue(0);


        level.startCase(new StartLevelGooseCase());

        for (int i = 1; i < nbCase; i++) {
            NoneGooseCase acase = new NoneGooseCase();
            acase.setNumber(i);
            level.addCase(acase);
        }

        level.endCase(new EndLevelGooseCase(nbCase));

        gooseLevelDao.save(level);

        return level;
    }



    @Transactional
    public GooseLevel generateMultiLevel(Integer strong,Long numLevel,Integer minAmount){
        GooseLevel level = new GooseLevel();
        level.setLevel(numLevel);
        level.setMinValue(minAmount);
        level.setMultiple(true);
        level.setNbMaxAdByPlay(6);
        level.setStrong(strong);
        level.setValue(minAmount);


        int nbCase = 1;
        for (int i = 1; i <= strong; i++) {
            nbCase+=i;
        }

        level.startCase(new StartLevelGooseCase());

        for (int i = 1; i < nbCase; i++) {
            NoneGooseCase acase = new NoneGooseCase();
            acase.setNumber(i);
            level.addCase(acase);
        }

        level.endCase(new EndLevelGooseCase(nbCase));

        gooseLevelDao.save(level);

        return level;
    }

    @Transactional
    public GooseLevelGame getGooseLevel(Long levelId)  {
        GooseLevel gooseLevel = gooseLevelDao.get(levelId);
        return new GooseLevelGame(gooseLevel);
    }

    @Transactional
    public List<GooseLevelGame> search(Integer level, Boolean multiple) {
        List<GooseLevel> levels =  gooseLevelDao.findLevel(level,multiple);
        List<GooseLevelGame> gooseLevelGames = new ArrayList<GooseLevelGame>();
        for (GooseLevel gooseLevel : levels) {
            gooseLevelGames.add(new GooseLevelGame(gooseLevel));
        }
        return gooseLevelGames;
    }
}
