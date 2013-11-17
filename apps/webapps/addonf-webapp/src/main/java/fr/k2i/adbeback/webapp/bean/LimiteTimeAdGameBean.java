package fr.k2i.adbeback.webapp.bean;

import java.io.Serializable;
import java.util.List;

public class LimiteTimeAdGameBean implements Serializable{
	private static final long serialVersionUID = 583944580843347770L;
	private Integer score;
	private StatusGame status;
	private String message;


    private List<PlayerGooseGame> gooseGames;
    private Integer userToken;

    public List<PlayerGooseGame> getGooseGames() {
        return gooseGames;
    }

    public void setGooseGames(List<PlayerGooseGame> gooseGames) {
        this.gooseGames = gooseGames;
    }

    public Integer getUserToken() {
        return userToken;
    }

    public void setUserToken(Integer userToken) {
        this.userToken = userToken;
    }

    public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public StatusGame getStatus() {
		return status;
	}
	public void setStatus(StatusGame status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
