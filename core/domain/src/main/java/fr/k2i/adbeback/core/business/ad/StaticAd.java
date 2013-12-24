package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.IMetaData;

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
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.Ad.Discrimator.STATIC_AD)
public class StaticAd extends Ad{

    @Column(name = IMetaData.ColumnMetadata.Ad.IMG)
    protected String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
