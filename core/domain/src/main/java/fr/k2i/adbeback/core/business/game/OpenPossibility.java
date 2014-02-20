package fr.k2i.adbeback.core.business.game;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("OpenPossibility")
public class OpenPossibility extends Possibility {
    @Column(name = "answer_text")
	private String answerText;
    @Column(name = "answer_image")
    private String answerImage;
}
