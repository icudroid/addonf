package fr.k2i.adbeback.core.business.goosegame;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Win")
public class WinGooseCase extends GooseCase {

	private static final long serialVersionUID = 5373444898760166087L;

    @Override
    public int ihmValue() {
        return 7;
    }

	@Override
	public String toString() {
		return "WinGooseCase [id=" + id + ", level=" + level + "]";
	}

}
