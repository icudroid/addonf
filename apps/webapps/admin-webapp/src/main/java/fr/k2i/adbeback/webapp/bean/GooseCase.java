package fr.k2i.adbeback.webapp.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 07/12/13
 * Time: 09:35
 * To change this template use File | Settings | File Templates.
 */
public class GooseCase extends LevelCase implements Serializable{
    private fr.k2i.adbeback.core.business.goosegame.GooseCase gooseCase;

    public GooseCase() { }

    public int getType(){
        return gooseCase.ihmValue();
    }

    public Integer getNumber(){
        return gooseCase.getNumber();
    }

    public Long getId(){
        return gooseCase.getId();
    }

    public void setId(Long id){
        gooseCase.setId(id);
    }

    public void setType(int type){
        this.type = type;
    }

    public void setNumber(Integer number){
        gooseCase.setNumber(number);
    }

    public GooseCase(fr.k2i.adbeback.core.business.goosegame.GooseCase gooseCase){
        this.gooseCase = gooseCase;
    }
}
