package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 19/12/13
 * Time: 20:59
 * To change this template use File | Settings | File Templates.
 */
@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.Ad.Discrimator.STATIC_AD)
public class StaticAd extends Ad{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoAd)) return false;
        if (!super.equals(o)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result;
        return result;
    }
}
