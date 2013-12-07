package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.dao.jpa.GooseLevelDao;
import fr.k2i.adbeback.service.GooseGameManager;
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
    public void getGooseLevel(GooseLevel gooseLevel) throws Exception {
        List<GooseCase> levelCases = gooseGameManager.getLevelCases(gooseLevel);

        int size = levelCases.size();
        int strong = gooseLevel.getStrong();

        GooseCase[][] matrice = new GooseCase[strong][strong];

        int i = 0, x = 0, y = 0, direction = 0 , incr = strong;

        while (incr>=1){
            for (int j = 0; j < incr; j++,i++) {
                matrice[x][y] = levelCases.get(i);
                if(j==incr-1){
                    direction+=90;
                }
                x+=(int)Math.cos(Math.toRadians(direction));
                y+=(int)Math.sin(Math.toRadians(direction));
            }
            incr--;
        }

    }
}
