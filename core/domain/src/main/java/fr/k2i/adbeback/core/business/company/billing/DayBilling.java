package fr.k2i.adbeback.core.business.company.billing;

import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

import javax.persistence.*;

/**
 * User: dimitri
 * Date: 07/05/14
 * Time: 10:35
 * Goal:
 */


@Data
@Entity
@Table(name = "day_billing")
public class DayBilling extends BaseObject {
    @Id
    @SequenceGenerator(name = "DayBilling_Gen", sequenceName = "DayBilling_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DayBilling_Gen")
    protected Long id;

    private Integer dayOfMonth;

    private Double amount;

    private Long nbTransaction;

    @ManyToOne
    private MonthBilling monthBilling;

}
