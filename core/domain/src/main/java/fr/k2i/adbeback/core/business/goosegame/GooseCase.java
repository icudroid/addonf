package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import fr.k2i.adbeback.core.business.BaseObject;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "goose_case")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class GooseCase extends BaseObject implements Serializable {

	private static final long serialVersionUID = 6585235053865504234L;
	protected Long id;
	protected GooseLevel level;
	protected Integer number;


    public abstract int ihmValue();

//	@SequenceGenerator(name = "GooseCase_Gen", sequenceName = "GooseCase_Sequence")
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GooseCase_Gen")
	@GeneratedValue(strategy = GenerationType.AUTO)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "GOOSELEVEL_ID")
	public GooseLevel getLevel() {
		return level;
	}

	public void setLevel(GooseLevel level) {
		this.level = level;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GooseCase)) return false;

        GooseCase gooseCase = (GooseCase) o;

        if (level != null ? !level.equals(gooseCase.level) : gooseCase.level != null) return false;
        if (number != null ? !number.equals(gooseCase.number) : gooseCase.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
