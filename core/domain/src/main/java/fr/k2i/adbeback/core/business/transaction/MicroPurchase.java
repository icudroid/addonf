package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.game.AdGame;
import lombok.Data;

import javax.persistence.*;

/**
 * User: dimitri
 * Date: 16/09/14
 * Time: 13:53
 * Goal:
 */
@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.Transaction.Discrimator.MICRO_PURCHASE)
public class MicroPurchase extends Debit implements IMicroPurchase {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Transaction.AD_GAME)
    private AdGame adGame;



}
