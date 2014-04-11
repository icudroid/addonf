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
}
