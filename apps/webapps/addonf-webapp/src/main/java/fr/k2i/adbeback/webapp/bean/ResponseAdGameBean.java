package fr.k2i.adbeback.webapp.bean;

import java.io.Serializable;

public class ResponseAdGameBean implements Serializable{
	private static final long serialVersionUID = -7776502181258631402L;
	
	private Integer score;
	private Boolean correct;
	private StatusGame status;

    private Integer userToken;
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Boolean getCorrect() {
		return correct;
	}
	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}
	public StatusGame getStatus() {
		return status;
	}
	public void setStatus(StatusGame status) {
		this.status = status;
	}

    public Integer getUserToken() {
        return userToken;
    }

    public void setUserToken(Integer userToken) {
        this.userToken = userToken;
    }
}
