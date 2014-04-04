package fr.k2i.adbeback.core.business.goosegame;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("Jail")
public class JailGooseCase extends GooseCase {

	private static final long serialVersionUID = 142765339504599666L;

    @Override
    public int ihmValue() {
        return 6;
    }

	@Override
	public String toString() {
		return "JailGooseCase [id=" + id + ", level=" + level + ", number="
				+ number + "]";
	}


}
