package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 16/09/14
 * Time: 13:41
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.WALLET)
public class Wallet extends BaseObject implements Serializable {
    @Id
    @SequenceGenerator(name = "Wallet_Gen", sequenceName = "Wallet_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Wallet_Gen")
    private Long id;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="WALLET_ID")
    private List<Transaction> transactions;

    private Long adAmount;

    public void addTransaction(Transaction tr) {
        if(transactions == null){
            transactions = new ArrayList<>();
        }
        transactions.add(tr);
    }
}
