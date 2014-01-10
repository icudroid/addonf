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
@DiscriminatorValue(IMetaData.ColumnMetadata.Ad.Discrimator.AUDIO_AD)
public class AudioAd extends Ad{

}
