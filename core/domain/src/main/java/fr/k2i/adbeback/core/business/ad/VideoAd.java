package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.Column;
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
@DiscriminatorValue(IMetaData.ColumnMetadata.Ad.Discrimator.VIDEO_AD)
public class VideoAd extends Ad{
    @Column(name = IMetaData.ColumnMetadata.Ad.AD_FILE_ENCODED)
    protected Boolean adFileEncoded = false;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoAd)) return false;
        if (!super.equals(o)) return false;

        VideoAd videoAd = (VideoAd) o;

        if (adFileEncoded != null ? !adFileEncoded.equals(videoAd.adFileEncoded) : videoAd.adFileEncoded != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (adFileEncoded != null ? adFileEncoded.hashCode() : 0);
        return result;
    }
}
