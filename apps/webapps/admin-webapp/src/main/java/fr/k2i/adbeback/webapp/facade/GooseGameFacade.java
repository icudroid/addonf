package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.dao.jpa.GooseCaseDao;
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


    @Autowired
    private GooseCaseDao gooseCaseDao;


    @Transactional
    public GooseLevelGame generateLotteryLevel(Integer nbCase, Integer numLevel) {
        LotteryGooseLevel level = new LotteryGooseLevel();
        level.setLevel(numLevel);
        level.setNbMaxAdByPlay(nbCase);
        level.setMinScore(nbCase);

        level.startCase(new StartLevelGooseCase());

        for (int i = 1; i < nbCase; i++) {
            NoneGooseCase acase = new NoneGooseCase();
            acase.setNumber(i);
            level.addCase(acase);
        }

        level.endCase(new EndLevelGooseCase(nbCase));

        return new GooseLevelGame(gooseLevelDao.save(level));
    }


    @Transactional
    public GooseLevelGame generateDiceLevel(Integer nbCase, Integer numLevel) {
        DiceGooseLevel level = new DiceGooseLevel();
        level.setLevel(numLevel);
        level.setNbMaxAdByPlay(nbCase);
        level.setMaxScore(nbCase);

        level.startCase(new StartLevelGooseCase());

        for (int i = 1; i < nbCase; i++) {
            NoneGooseCase acase = new NoneGooseCase();
            acase.setNumber(i);
            level.addCase(acase);
        }

        level.endCase(new EndLevelGooseCase(nbCase));

        return new GooseLevelGame(gooseLevelDao.save(level));
    }


    @Transactional
    public GooseLevelGame generateSingleLevel(Integer nbCase,Integer numLevel,Integer nbError){
        SingleGooseLevel level = new SingleGooseLevel();
        level.setLevel(numLevel);
        level.setNbMaxAdByPlay(nbCase+nbError);
        level.setMinScore(nbCase);

        level.startCase(new StartLevelGooseCase());

        for (int i = 1; i < nbCase; i++) {
            NoneGooseCase acase = new NoneGooseCase();
            acase.setNumber(i);
            level.addCase(acase);
        }

        level.endCase(new EndLevelGooseCase(nbCase));

        return new GooseLevelGame(gooseLevelDao.save(level));
    }



    @Transactional
    public GooseLevelGame generateMultiLevel(Integer strong,Integer numLevel,Integer minAmount){
        MultiGooseLevel level = new MultiGooseLevel();
        level.setLevel(numLevel);
        level.setMinValue(minAmount);
        level.setNbMaxAdByPlay(6);
        level.setStrong(strong);
        level.setValue(minAmount);


        int nbCase = 0;
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

        return new GooseLevelGame(gooseLevelDao.save(level));
    }

    @Transactional
    public GooseLevelGame getGooseLevel(Long levelId)  {
        GooseLevel gooseLevel = gooseLevelDao.get(levelId);
        return new GooseLevelGame(gooseLevel);
    }

    @Transactional
    public List<GooseLevelGame> search(Integer level, Class<? extends GooseLevel> multiple) {
        List<? extends GooseLevel> levels =  gooseLevelDao.findLevel(level,multiple);
        List<GooseLevelGame> gooseLevelGames = new ArrayList<GooseLevelGame>();
        for (GooseLevel gooseLevel : levels) {
            gooseLevelGames.add(new GooseLevelGame(gooseLevel));
        }
        return gooseLevelGames;
    }

    @Transactional
    public void modifyCaseType(Long caseId, Integer type) {
        gooseCaseDao.updateType(caseId, type);
    }

    @Transactional
    public void modifyCaseTypeToJump(Long caseId, Integer jumpTo) {
        GooseCase gooseCase = gooseCaseDao.get(caseId);
        GooseLevel level = gooseCase.getLevel();
        List<GooseCase> gooseCases = level.getGooseCases();
        for (GooseCase aCase : gooseCases) {
            if(aCase.getNumber() == jumpTo){
                gooseCaseDao.updateTypeJumpTo(caseId, aCase.getId());
                return;
            }
        }

    }

    @Transactional
    public void deleteLevel(Long levelId) {
        gooseLevelDao.remove(levelId);
    }

    @Transactional
    public void modifyMinAmount(Long levelId, Integer minAmount) {
        gooseLevelDao.modifyLevelMinAmount(levelId,minAmount);
    }


}
