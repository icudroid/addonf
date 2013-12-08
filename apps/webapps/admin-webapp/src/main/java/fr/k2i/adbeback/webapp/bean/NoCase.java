package fr.k2i.adbeback.webapp.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 08/12/13
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
public class NoCase extends LevelCase implements Serializable {

    public NoCase(){
        super();
    }

    @Override
    public int getType() {
        return -1;
    }
}
