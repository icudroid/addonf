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
    protected String uid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdServiceBean)) return false;

        AdServiceBean that = (AdServiceBean) o;

        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}
