package fr.k2i.adbeback.webapp.bean;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 11/12/13
 * Time: 14:05
 * Goal:
 */
public class JsonResultError implements Serializable {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public JsonResultError(String e){
        error = e;
    }

    public static JsonResultError noError(){
        return new JsonResultError("no");
    }
}
