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
    private Long id;
    protected String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdServiceBean)) return false;

        AdServiceBean that = (AdServiceBean) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
