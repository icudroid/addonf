package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dimitri
 * Date: 16/09/14
 * Time: 13:53
 * Goal:
 */
@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.Transaction.Discrimator.EMPREINT)
public class Empreint extends Transaction{
    private Integer adAmount;
    private Integer adAmountLeft;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Transaction.ORDER_ID)
    private Order order;

    @OneToMany(cascade = CascadeType.ALL,targetEntity = Credit.class,mappedBy = "empreint")
    @JoinColumn(name = IMetaData.ColumnMetadata.Transaction.CREDIT_ID)
    private List<Credit> credits = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private EmpreintStatus status;

    @OneToMany(cascade = CascadeType.ALL,targetEntity =TransactionHistory.class)
    @JoinColumn(name = IMetaData.ColumnMetadata.Transaction.HISTORY_ID)
    private List<TransactionHistory> histories  = new ArrayList<>();

    public void addCredit(Credit credit) {
        if(credits == null){
            credits = new ArrayList<>();
        }
        credits.add(credit);

        adAmountLeft-=credit.getAdAmount();

        if(adAmountLeft>0) {
            //histories.add(new TransactionHistory(credit.getAdGame().getGenerated(), TransactionAction.REFILLED));
        }else{
            histories.add(new TransactionHistory(credit.getAdGame().getGenerated(), TransactionAction.FULL_REFILLED));
            status = EmpreintStatus.ENDED;
        }
    }

}
