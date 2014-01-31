package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 30/01/14
 * Time: 16:58
 * Goal:
 */
@Data
public class StatistBeanSearch implements Serializable{
    private Date start;
    private Date end;

    private TypeStat type;

    private Long idAd;
    private Long serviceId;

    private boolean global;
}
