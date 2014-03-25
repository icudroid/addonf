package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.player.Player;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "adgame")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractAdGame extends BaseObject implements Serializable {

	protected static final long serialVersionUID = -2713151389131420230L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

    @OneToMany(mappedBy = "adGame",cascade=CascadeType.ALL)
    @MapKey(name = "number")
    protected Map<Integer, AdChoise> choises;

    @Temporal(TemporalType.TIMESTAMP)
	protected Date generated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SCORE_ID")
    protected AdScore score;

    @Enumerated(EnumType.STRING)
	protected StatusGame statusGame = StatusGame.Playing;

    @ManyToOne
    @JoinColumn(name="PLAYER_ID")
    protected Player player;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choises == null) ? 0 : choises.hashCode());
		result = prime * result
				+ ((generated == null) ? 0 : generated.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result
				+ ((statusGame == null) ? 0 : statusGame.hashCode());
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
		AbstractAdGame other = (AbstractAdGame) obj;
		if (choises == null) {
			if (other.choises != null)
				return false;
		} else if (!choises.equals(other.choises))
			return false;
		if (generated == null) {
			if (other.generated != null)
				return false;
		} else if (!generated.equals(other.generated))
			return false;
/*		if (medias == null) {
			if (other.medias != null)
				return false;
		} else if (!medias.equals(other.medias))
			return false;*/
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (statusGame != other.statusGame)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractAdGame [id=" + id + ", choises=" + choises + ", generated="
				+ generated + ", score=" + score //+ ", medias=" + medias
				+ ", statusGame=" + statusGame + "]";
	}



}
