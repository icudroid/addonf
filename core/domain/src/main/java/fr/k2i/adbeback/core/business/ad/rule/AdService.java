package fr.k2i.adbeback.core.business.ad.rule;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
}
