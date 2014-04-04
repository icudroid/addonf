package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.player.Player;
import lombok.Data;

@Data
@Entity
@Table(name = "goose_token")
public class GooseToken extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8708579369170723785L;

    @Id
    @SequenceGenerator(name = "GooseToken_Gen", sequenceName = "GooseToken_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GooseToken_Gen")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "GOOSECASE_ID")
	private GooseCase gooseCase;

    @ManyToOne()
    @JoinColumn(name = "PLAYER_ID")
	private Player player;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gooseCase == null) ? 0 : gooseCase.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		GooseToken other = (GooseToken) obj;
		if (gooseCase == null) {
			if (other.gooseCase != null)
				return false;
		} else if (!gooseCase.equals(other.gooseCase))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GooseToken [id=" + id + ", gooseCase=" + gooseCase
				+ ", player=" + player + "]";
	}

}
