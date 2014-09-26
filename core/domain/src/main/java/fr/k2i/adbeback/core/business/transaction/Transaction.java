package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 16/09/14
 * Time: 13:48
 * Goal:
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = IMetaData.TableMetadata.TRANSACTION)
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction extends BaseObject implements Serializable{

    public static final Double AD_AMOUNT = 0.24;
    public static final Double PERCENT_ADD_TO_END_DATE = 1.40;
    public static final Integer NB_AD_BY_DAY = 6;
    public static final Integer MAX_DAYS_BORROW = 365;
    public static final Integer MAX_AD_AMOUNT_BORROW = new Double(AD_AMOUNT * NB_AD_BY_DAY * MAX_DAYS_BORROW).intValue();


    @Id
    @SequenceGenerator(name = "Transaction_Gen", sequenceName = "Transaction_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Transaction_Gen")
    private Long id;

}
