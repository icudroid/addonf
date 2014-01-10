package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = IMetaData.TableMetadata.GOOSE_LEVEL)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.GooseLevel.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract class GooseLevel extends BaseObject implements Serializable {

	protected static final long serialVersionUID = 3339924782068634755L;
	protected Long id;
	protected Integer level;
    protected boolean limitedTime;
    protected Integer nbMaxAdByPlay;
	protected StartLevelGooseCase startCase;
	protected EndLevelGooseCase endCase;
	protected List<GooseCase> gooseCases;


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

	@Id
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

	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "START_ID")
	public StartLevelGooseCase getStartCase() {
		return startCase;
	}

	public void setStartCase(StartLevelGooseCase startCase) {
		this.startCase = startCase;
	}

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinColumn(name = "END_ID")
	public EndLevelGooseCase getEndCase() {
		return endCase;
	}

	public void setEndCase(EndLevelGooseCase endCase) {
		this.endCase = endCase;
	}

	@Override
	public String toString() {
		return "GooseLevel [id=" + id + ", level=" + level
				+ ", startCase=" + startCase + ", endCase=" + endCase
				;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GooseLevel)) return false;

        GooseLevel that = (GooseLevel) o;

        if (level != null ? !level.equals(that.level) : that.level != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        return result;
    }


    public void startCase(StartLevelGooseCase start) {
        startCase = start;
        startCase.setLevel(this);
        if(gooseCases == null){
            gooseCases = new ArrayList<GooseCase>();
        }
        gooseCases.add(startCase);

    }

    public void endCase(EndLevelGooseCase endLevelGooseCase) {
        endCase = endLevelGooseCase;
        endCase.setLevel(this);
        if(gooseCases == null){
            gooseCases = new ArrayList<GooseCase>();
        }
        gooseCases.add(endCase);
    }

    public void addCase(GooseCase acase) {
        acase.setLevel(this);
        if(gooseCases == null){
            gooseCases = new ArrayList<GooseCase>();
        }
        gooseCases.add(acase);
    }
}
