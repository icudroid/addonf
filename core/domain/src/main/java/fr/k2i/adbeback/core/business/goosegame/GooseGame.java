package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

@Data
@Entity
@Table(name = "goose_game")
public class GooseGame extends BaseObject implements Serializable {
	private static final long serialVersionUID = 5989474986523224103L;

    @Id
    @SequenceGenerator(name = "GooseGame_Gen", sequenceName = "GooseGame_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GooseGame_Gen")
	private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "GOOSE_GAME_ID")
	private List<GooseLevel> levels;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((levels == null) ? 0 : levels.hashCode());
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
		GooseGame other = (GooseGame) obj;
		if (levels == null) {
			if (other.levels != null)
				return false;
		} else if (!levels.equals(other.levels))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GooseGame [id=" + id + ", levels=" + levels + "]";
	}




}
