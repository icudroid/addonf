package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class PossibilityBean implements Serializable{
	private Long id;
	private Integer type;
	private String answerText;
    private String answerImage;
}
