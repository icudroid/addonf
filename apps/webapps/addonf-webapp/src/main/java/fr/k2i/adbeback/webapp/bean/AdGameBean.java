package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdGameBean implements Serializable{
	private static final long serialVersionUID = -7410887579362937509L;
    private Integer typeGame;
    private Integer score;
	private Integer totalAds;
	private Long timeLimite;
	private List<AdBean> game;
    private List<PlayerGooseGame> gooseGames;
    private Integer userToken;
    private boolean multiple;
    private String logoMedia;
    private boolean showSplashScreen;
}
