package fr.k2i.adbeback.webapp.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 08/12/13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class LevelCase implements Serializable{
    protected int type;

    public abstract  int getType();
}
