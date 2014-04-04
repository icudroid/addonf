package fr.k2i.adbeback.core.business.goosegame;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("None")
public class NoneGooseCase extends GooseCase {

	private static final long serialVersionUID = 3725803843441617038L;

    @Override
    public int ihmValue() {
        return 8;
    }


	@Override
	public String toString() {
		return "NoneGooseCase";
	}

}
