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
    @Id
    @SequenceGenerator(name = "Transaction_Gen", sequenceName = "Transaction_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Transaction_Gen")
    private Long id;

}
