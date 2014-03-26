package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class AdBean implements Serializable{
	private static final long serialVersionUID = -427599826972908154L;
	private String question;
	private List<PossibilityBean> possibilities;
    private Long duration;
    private TypeAd type;
    private boolean multiResponse;
    private String addonText;
    private String btnValidText;

    public enum TypeAd{
        VIDEO,STATIC,AUDIO;
    }

}
