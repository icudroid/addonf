package fr.k2i.adbeback.core.business.game;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("OpenPossibility")
public class OpenPossibility extends Possibility {
	private String answerText;
    private String answerImage;
}
