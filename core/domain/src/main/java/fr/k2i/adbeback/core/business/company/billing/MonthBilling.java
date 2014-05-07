package fr.k2i.adbeback.core.business.company.billing;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.company.Company;
import fr.k2i.adbeback.core.business.user.Media;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * User: dimitri
 * Date: 07/05/14
 * Time: 10:30
 * Goal:
 */
@Data
@Entity
@Table(name = "month_billing")
public class MonthBilling extends BaseObject{
    @Id
    @SequenceGenerator(name = "MonthBilling_Gen", sequenceName = "MonthBilling_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MonthBilling_Gen")
    protected Long id;

    private Double sum;

    @Embedded
    private Month month;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monthBilling")
    private Set<DayBilling> dayBillings;

    @ManyToOne
    private Company company;


    public MonthBilling(){};
    public MonthBilling(int month , int year, Company company) {
        this.month = new Month(month,year);
        this.sum = 0D;
        this.company = company;
    }

}
