package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.partener.Merchant;
import fr.k2i.adbeback.core.business.user.Media;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 16/09/14
 * Time: 13:59
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.ORDER)
public class Order  extends BaseObject implements Serializable {
    @Id
    @SequenceGenerator(name = "Order_Gen", sequenceName = "Order_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Gen")
    private Long id;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.Order.MEDIA)
    private Media media;

}
