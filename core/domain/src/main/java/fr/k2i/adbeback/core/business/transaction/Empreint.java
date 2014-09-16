package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
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
    @JoinColumn(name = IMetaData.ColumnMetadata.Transaction.JOIN)
    private Order order;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Transaction.JOIN)
    private List<Credit> credits;
}
