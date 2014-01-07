package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.goosegame.IMultiGooseLevel;
import fr.k2i.adbeback.core.business.goosegame.MultiGooseLevel;
import fr.k2i.adbeback.core.business.goosegame.NoneGooseCase;

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

    public Integer getLevel(){
        return level.getLevel();
    }

    public Long getLevelId(){
        return level.getId();
    }

    public Integer getNbMaxAdByPlay(){
        return level.getNbMaxAdByPlay();
    }

    public Integer getStrong(){
        if(level instanceof IMultiGooseLevel){
            return ((MultiGooseLevel)level).getStrong();
        }else{
            return null;
        }
    }

    public Integer getAmount(){
        if(level instanceof IMultiGooseLevel){
            return ((MultiGooseLevel)level).getValue();
        }else{
            return null;
        }
    }

    public Integer getMinAmountWin(){
        if(level instanceof IMultiGooseLevel){
            return ((MultiGooseLevel)level).getMinValue();
        }else{
            return null;
        }
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
        if(level instanceof IMultiGooseLevel){
            ((MultiGooseLevel)level).setStrong(strong);
        }
    }

    public void setAmount(Integer amount){
        if(level instanceof IMultiGooseLevel){
            ((MultiGooseLevel)level).setValue(amount);
        }
    }

    public void setMinAmountWin(Integer minAmount){
        if(level instanceof IMultiGooseLevel){
            ((MultiGooseLevel)level).setMinValue(minAmount);
        }
    }

    private int calculateStrong(int size){
        int count = 0;
        int strong = 1;
        do{
            count+=strong;
            strong++;
        }while (size > count+1);

        return strong-1;
    }

    public LevelCase[][] getMatrice(){
        int size = this.gooseCases.size();
        int strong = calculateStrong(size);

        LevelCase[][] matrice = new LevelCase[strong][strong];

        for (int i = 0; i < strong; i++) {
            for (int j = 0; j < strong; j++) {
                matrice[i][j] = new NoCase();
            }
        }

        int i = 1, x = 0, y = 0, direction = 0 , incr = strong;

        while (incr>=1){
            for (int j = 0; j < incr; j++,i++) {
                if(i>=this.gooseCases.size()){
                    return matrice;
                }
                matrice[y][x] = this.gooseCases.get(i);
                if(j==incr-1){
                    direction+=90;
                }
                x+=(int)Math.cos(Math.toRadians(direction));
                y+=(int)Math.sin(Math.toRadians(direction));
            }
            incr--;
        }

        return matrice;
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
