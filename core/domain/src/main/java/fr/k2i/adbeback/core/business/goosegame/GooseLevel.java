package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;

@Entity
@Table(name = "goose_level")
public class GooseLevel extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3339924782068634755L;
	private Long id;
	private Integer level;
	private Integer value;
    private Integer nbMaxAdByPlay;
    //private Integer minScore;
	private StartLevelGooseCase startCase;
	private EndLevelGooseCase endCase;
	private List<GooseCase> gooseCases;
    private boolean multiple;
    private Integer strong;
    private Integer minValue;

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getStrong() {
        return strong;
    }

    public void setStrong(Integer strong) {
        this.strong = strong;
    }

/*    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }*/

    public Integer getNbMaxAdByPlay() {
        return nbMaxAdByPlay;
    }

    public void setNbMaxAdByPlay(Integer nbMaxAdByPlay) {
        this.nbMaxAdByPlay = nbMaxAdByPlay;
    }

    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "GOOSELEVEL_ID")
    @OrderBy("number")
	public List<GooseCase> getGooseCases() {
		return gooseCases;
	}

	public void setGooseCases(List<GooseCase> gooseCases) {
		this.gooseCases = gooseCases;
	}

//	@SequenceGenerator(name = "GooseLevel_Gen", sequenceName = "GooseLevel_Sequence")
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GooseLevel_Gen")
	@GeneratedValue(strategy = GenerationType.AUTO)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "START_ID")
	public StartLevelGooseCase getStartCase() {
		return startCase;
	}

	public void setStartCase(StartLevelGooseCase startCase) {
		this.startCase = startCase;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "END_ID")
	public EndLevelGooseCase getEndCase() {
		return endCase;
	}

	public void setEndCase(EndLevelGooseCase endCase) {
		this.endCase = endCase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endCase == null) ? 0 : endCase.hashCode());
		result = prime * result
				+ ((gooseCases == null) ? 0 : gooseCases.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result
				+ ((startCase == null) ? 0 : startCase.hashCode());
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
		GooseLevel other = (GooseLevel) obj;
		if (endCase == null) {
			if (other.endCase != null)
				return false;
		} else if (!endCase.equals(other.endCase))
			return false;
		if (gooseCases == null) {
			if (other.gooseCases != null)
				return false;
		} else if (!gooseCases.equals(other.gooseCases))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (startCase == null) {
			if (other.startCase != null)
				return false;
		} else if (!startCase.equals(other.startCase))
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
		return "GooseLevel [id=" + id + ", level=" + level + ", value=" + value
				+ ", startCase=" + startCase + ", endCase=" + endCase
				;
	}


    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }


    public void startCase(StartLevelGooseCase start) {
        start.setLevel(this);
        if(gooseCases == null){
            gooseCases = new ArrayList<GooseCase>();
            gooseCases.add(start);
        }

    }

    public void endCase(EndLevelGooseCase endLevelGooseCase) {
        endLevelGooseCase.setLevel(this);
        endCase = endLevelGooseCase;
        gooseCases.add(endLevelGooseCase);
    }

    public void addCase(GooseCase acase) {
        acase.setLevel(this);
        gooseCases.add(acase);
    }
}
