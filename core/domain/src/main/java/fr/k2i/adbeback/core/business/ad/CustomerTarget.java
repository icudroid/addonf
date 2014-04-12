package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 11/04/14
 * Time: 10:29
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.CUSTOMER_TARGET)
public class CustomerTarget extends BaseObject implements Serializable {
    @Id
    @SequenceGenerator(name = "CustomerTarget_Gen", sequenceName = "CustomerTarget_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerTarget_Gen")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    public CustomerTarget(){}
    public CustomerTarget(Sex sex, AgeGroup ageGroup) {
        this.sex = sex;
        this.ageGroup = ageGroup;
    }
}
