package fr.k2i.adbeback.core.business.goosegame;

import java.io.Serializable;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "goose_case")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class GooseCase extends BaseObject implements Serializable {

	private static final long serialVersionUID = 6585235053865504234L;

    @Id
    @SequenceGenerator(name = "GooseCase_Gen", sequenceName = "GooseCase_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GooseCase_Gen")
    protected Long id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gooselevel_id")
	protected GooseLevel level;
	protected Integer number;


    public abstract int ihmValue();

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
