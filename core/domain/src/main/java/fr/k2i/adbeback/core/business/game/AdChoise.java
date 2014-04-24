package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import lombok.Data;

@Data
@Entity
@Table(name = "adchoise")
public class AdChoise extends BaseObject implements Serializable {
	private static final long serialVersionUID = -7659738703107950065L;

    @Id
    @SequenceGenerator(name = "AdChoise_Gen", sequenceName = "AdChoise_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdChoise_Gen")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "adchoise_id")
    private List<Possibility> possiblities;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AD_GAME_ID")
	private AbstractAdGame adGame;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "correct_for_adchoise_id")
	private List<Possibility> corrects;

	private String question;

	private Integer number;

    @ManyToOne
    @JoinColumn(name = "adrule_id")
    private AdService generatedBy;

    //prix de l'enchère gagnante
    private Double winBidPrice;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdChoise)) return false;

        AdChoise adChoise = (AdChoise) o;

        if (generatedBy != null ? !generatedBy.equals(adChoise.generatedBy) : adChoise.generatedBy != null)
            return false;
        if (id != null ? !id.equals(adChoise.id) : adChoise.id != null) return false;
        if (number != null ? !number.equals(adChoise.number) : adChoise.number != null) return false;
        if (question != null ? !question.equals(adChoise.question) : adChoise.question != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (generatedBy != null ? generatedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdChoise{" +
                "question='" + question + '\'' +
                ", number=" + number +
                '}';
    }
}
