package fr.k2i.adbeback.webapp.bean.enroll.adv;

import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 11/04/14
 * Time: 13:40
 * Goal:
 */
@Data
public class CustomerTargetCommand implements Serializable {
    private Sex sex;
    private AgeGroup ageGroup;
    private String uid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerTargetCommand)) return false;

        CustomerTargetCommand that = (CustomerTargetCommand) o;

        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}
