package fr.k2i.adbeback.core.business.game;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdResponse;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("OpenPossibility")
public class OpenPossibility extends Possibility {

    @Column(name = "answer_text")
	private String answerText;

    @Column(name = "answer_image")
    private String answerImage;

    @ManyToOne
    @JoinColumn(name = "ad_response_id")
    private AdResponse generatedBy;
}
