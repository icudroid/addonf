package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.player.Player;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "adgame")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractAdGame extends BaseObject implements Serializable {

	protected static final long serialVersionUID = -2713151389131420230L;
	protected Long id;
	protected Map<Integer, AdChoise> choises;
	protected Date generated;
	protected AdScore score;
	//protected Integer minScore;
	protected StatusGame statusGame = StatusGame.Playing;
	protected Player player;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "adGame",cascade=CascadeType.ALL)
	@MapKey(name = "number")
	public Map<Integer, AdChoise> getChoises() {
		return choises;
	}

	public void setChoises(Map<Integer, AdChoise> choises) {
		this.choises = choises;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getGenerated() {
		return generated;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SCORE_ID")
	public AdScore getScore() {
		return score;
	}

	public void setScore(AdScore score) {
		this.score = score;
	}

/*	@ManyToMany(targetEntity = Media.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(name = "adgame_media", joinColumns = @JoinColumn(name = "ADGAME_ID"), inverseJoinColumns = @JoinColumn(name = "MEDIA_ID"))
	public List<Media> getMedias() {
		return medias;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}*/

/*	public Integer getMinScore() {
		return minScore;
	}

	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}*/
	
	
	@Enumerated(EnumType.ORDINAL)
	public StatusGame getStatusGame() {
		return statusGame;
	}

	public void setStatusGame(StatusGame statusGame) {
		this.statusGame = statusGame;
	}

    @ManyToOne
    @JoinColumn(name="PLAYER_ID")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

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
