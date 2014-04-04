package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.ad.Ad;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "possibility")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class Possibility extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7871555951146460091L;

    @Id
    @SequenceGenerator(name = "Possibility_Gen", sequenceName = "Possibility_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Possibility_Gen")
	protected Long id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "AD_ID")
	protected Ad ad;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ad == null) ? 0 : ad.hashCode());
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
		Possibility other = (Possibility) obj;
		if (ad == null) {
			if (other.ad != null)
				return false;
		} else if (!ad.equals(other.ad))
			return false;
		return true;
	}

}
