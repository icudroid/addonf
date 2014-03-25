package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseAdGameBean implements Serializable{
	private static final long serialVersionUID = -7776502181258631402L;
	
	private Integer score;
	private Boolean correct;
	private StatusGame status;
    private String whereToGo;
    private Integer userToken;
}
