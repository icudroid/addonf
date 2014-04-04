package fr.k2i.adbeback.core.business.goosegame;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("StartLevel")
public class StartLevelGooseCase extends GooseCase {

	private static final long serialVersionUID = -5268929254361161407L;

    @Override
    public int ihmValue() {
        return 0;
    }

    public StartLevelGooseCase(){
        number = 0;
    }

	@Override
	public String toString() {
		return "StartLevelGooseCase [id=" + id + ", level=" + level + "]";
	}

}
