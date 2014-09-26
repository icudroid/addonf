package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 17/09/14
 * Time: 11:04
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.TRANSACTION_HISTORY)
public class TransactionHistory extends BaseObject implements Serializable {
    @Id
    @SequenceGenerator(name = "TransactionHistory_Gen", sequenceName = "TransactionHistory_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionHistory_Gen")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date executed;

    @Enumerated(EnumType.STRING)
    private TransactionAction action;


    public static TransactionHistory creation() {
        TransactionHistory res = new TransactionHistory();
        res.setExecuted(new Date());
        res.setAction(TransactionAction.ASKED);
        return res;
    }
}
