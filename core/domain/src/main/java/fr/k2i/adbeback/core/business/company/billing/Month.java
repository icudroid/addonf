package fr.k2i.adbeback.core.business.company.billing;

import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 07/05/14
 * Time: 10:32
 * Goal:
 */

@Data
@Embeddable
public class Month extends BaseObject implements Serializable {
    private Integer month;
    private Integer year;

    public Month(){}

    public Month(int month, int year) {
        this.month = month;
        this.year = year;
    }
}

