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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "level")
	protected Integer level;

    @Column(name = "limited_time")
    protected Boolean limitedTime;

    @Column(name = "nb_max_ad_by_play")
    protected Integer nbMaxAdByPlay;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "start_id")
    protected StartLevelGooseCase startCase;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "end_id")
    protected EndLevelGooseCase endCase;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gooselevel_id")
    @OrderBy("number")
	protected List<GooseCase> gooseCases;


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
