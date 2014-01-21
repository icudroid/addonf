package fr.k2i.adbeback.webapp.bean.adservice;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 21/01/14
 * Time: 10:19
 * Goal:
 */
@Data
public abstract class AdServiceBean implements Serializable {
    protected Date startDate;
    protected Date endDate;
    protected Integer maxDisplayByUser;
}
