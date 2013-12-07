package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.goosegame.GooseLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 06/12/13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class GooseLevelGame implements Serializable{

    GooseLevel level;
    List<GooseCase> gooseCases;

    public Long getLevel(){
        return level.getLevel();
    }

    public Long getLevelId(){
        return level.getId();
    }

    public Integer getNbMaxAdByPlay(){
        return level.getNbMaxAdByPlay();
    }

    public Integer getStrong(){
        return level.getStrong();
    }

    public Integer getAmount(){
        return level.getValue();
    }

    public Integer getMinAmountWin(){
        return level.getMinValue();
    }

    public List<GooseCase> getGooseCases() {
        return gooseCases;
    }

    public void setGooseCases(List<GooseCase> gooseCases) {
        this.gooseCases = gooseCases;
    }

    public void setLevelId(Long id){
        level.setId(id);
    }

    public void setNbMaxAdByPlay(Integer max){
        level.setNbMaxAdByPlay(max);
    }

    public void setStrong(Integer strong){
        level.setStrong(strong);
    }

    public void setAmount(Integer amount){
        level.setValue(amount);
    }

    public void setMinAmountWin(Integer minAmount){
        level.setMinValue(minAmount);
    }

    public GooseLevelGame(GooseLevel gooseLevel){
        this.level = gooseLevel;
        List<fr.k2i.adbeback.core.business.goosegame.GooseCase> cases = level.getGooseCases();
        this.gooseCases = new ArrayList<GooseCase>();
        for (fr.k2i.adbeback.core.business.goosegame.GooseCase aCase : cases) {
            this.gooseCases.add(new GooseCase(aCase));
        }
    }
}
