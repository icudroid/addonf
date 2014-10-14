package fr.k2i.adbeback.core.business.goosegame;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.GooseLevel.Discrimator.LOTTERY)
public class LotteryGooseLevel extends GooseLevel implements ILotteryGooseLevel {

    @Column(name = "score")
    protected Integer minScore;

	@Override
	public String toString() {
		return "LotteryGooseLevel [id=" + id + ", level=" + level
				+ ", startCase=" + startCase + ", endCase=" + endCase
				;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LotteryGooseLevel)) return false;

        LotteryGooseLevel that = (LotteryGooseLevel) o;

        if (level != null ? !level.equals(that.level) : that.level != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        return result;
    }

}
