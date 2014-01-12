package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.Ad;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
@Data
@Entity
public abstract class AdService extends AdRule{
    @Temporal(TemporalType.DATE)
    protected Date startDate;
    @Temporal(TemporalType.DATE)
    protected Date endDate;
    protected Integer maxDisplayByUser;
    protected Double price;

    @Column(name = IMetaData.ColumnMetadata.AdRule.QUESTION)
    protected String question;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    protected Ad ad;

}
