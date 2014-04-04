package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.player.Player;
import lombok.Data;

@Data
@Entity
@Table(name = "goose_win")
public class GooseWin extends BaseObject implements Serializable {
	private static final long serialVersionUID = -4925568112544086765L;


    @Id
    @SequenceGenerator(name = "GooseWin_Gen", sequenceName = "GooseWin_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GooseWin_Gen")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "gooselevel_id")
	private GooseLevel gooseLevel;
	private Integer value;

    @Enumerated(EnumType.STRING)
	private WinStatus status = WinStatus.NotTranfered;

    @Temporal(TemporalType.TIMESTAMP)
	private Date windate = new Date();


    @ManyToOne
    @JoinColumn(name="PLAYER_ID")
	private Player player;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gooseLevel == null) ? 0 : gooseLevel.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		GooseWin other = (GooseWin) obj;
		if (gooseLevel == null) {
			if (other.gooseLevel != null)
				return false;
		} else if (!gooseLevel.equals(other.gooseLevel))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GooseWin [id=" + id + ", gooseLevel=" + gooseLevel + ", value="
				+ value + "]";
	}

}
