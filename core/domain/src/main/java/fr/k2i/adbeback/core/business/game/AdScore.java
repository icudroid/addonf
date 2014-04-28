package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

@Data
@Entity
@Table(name = "ad_score")
public class AdScore extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1613488681898448793L;

    @Id
    @SequenceGenerator(name = "AdScore_Gen", sequenceName = "AdScore_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdScore_Gen")
	private Long id;

	private Integer score;

    @OneToMany(mappedBy = "adScore",cascade=CascadeType.ALL)
    @MapKey(name = "number")
	private Map<Integer,AdResponsePlayer> answers;

    @OneToOne(mappedBy = "score")
    private AbstractAdGame game;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdScore other = (AdScore) obj;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AdScore [id=" + id + ", score=" + score + "]";
	}

}
