package fr.k2i.adbeback.core.business.goosegame;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@DiscriminatorValue("Jump")
public class JumpGooseCase extends GooseCase {
	private static final long serialVersionUID = 7152651220130031461L;


    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "GOOSEJUMP_ID")
    private GooseCase jumpTo;

    @Override
    public int ihmValue() {
        return 1;
    }



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jumpTo == null) ? 0 : jumpTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JumpGooseCase other = (JumpGooseCase) obj;
		if (jumpTo == null) {
			if (other.jumpTo != null)
				return false;
		} else if (!jumpTo.equals(other.jumpTo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JumpGooseCase [jumpTo=" + jumpTo + ", id=" + id + ", level="
				+ level + "]";
	}
}
