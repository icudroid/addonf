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
@DiscriminatorValue(IMetaData.ColumnMetadata.GooseLevel.Discrimator.MULTI)
public class MultiGooseLevel extends GooseLevel implements IMultiGooseLevel{

	protected Integer value;
    protected Integer strong;
    protected Integer minValue;

	@Override
	public String toString() {
		return "GooseLevel [id=" + id + ", level=" + level + ", value=" + value
				+ ", startCase=" + startCase + ", endCase=" + endCase
				;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiGooseLevel)) return false;

        MultiGooseLevel that = (MultiGooseLevel) o;

        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (strong != null ? !strong.equals(that.strong) : that.strong != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (strong != null ? strong.hashCode() : 0);
        result = 31 * result + (minValue != null ? minValue.hashCode() : 0);
        return result;
    }

}
