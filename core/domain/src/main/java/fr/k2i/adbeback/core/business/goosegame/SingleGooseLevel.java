package fr.k2i.adbeback.core.business.goosegame;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.GooseLevel.Discrimator.SINGLE)
public class SingleGooseLevel extends GooseLevel implements ISingleGooseLevel {

    protected Integer minScore;

	@Override
	public String toString() {
		return "GooseLevel [id=" + id + ", level=" + level
				+ ", startCase=" + startCase + ", endCase=" + endCase
				;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleGooseLevel)) return false;

        SingleGooseLevel that = (SingleGooseLevel) o;

        if (level != null ? !level.equals(that.level) : that.level != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        return result;
    }

}
