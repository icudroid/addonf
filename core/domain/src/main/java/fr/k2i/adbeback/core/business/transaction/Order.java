package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.partener.Merchant;
import fr.k2i.adbeback.core.business.user.Media;
import lombok.*;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dimitri
 * Date: 16/09/14
 * Time: 13:59
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.ORDER)
//@Builder
public class Order extends BaseObject{
    @Id
    @SequenceGenerator(name = "Order_Gen", sequenceName = "Order_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Gen")
    private Long id;

    private Double amount;

    @Column(length = 6)
    private String currentCode;

    private String referenceMedia;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Order.JOIN)
    private List<MerchantProduct> products = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.Order.MEDIA)
    private Media media;

    @Transient
    public List<String> toProductsString() {
        List<String> res = new ArrayList<>();
        for (MerchantProduct product : products) {
            res.add(product.getProduct());
        }
        return res;
    }

    public static BuilderOrder builder() {
        return new BuilderOrder();
    }

    public static class BuilderOrder{
        private Double amount;
        private String currentCode;
        private String referenceMedia;
        private List<MerchantProduct> products = new ArrayList<>();
        private Date orderDate;
        private Media media;


        public BuilderOrder(){

        }

        public BuilderOrder amount(Double amount){
            this.amount = amount;
            return this;
        }

        public BuilderOrder currentCode(String currentCode){
            this.currentCode = currentCode;
            return this;
        }

        public BuilderOrder referenceMedia(String referenceMedia){
            this.referenceMedia = referenceMedia;
            return this;
        }

        public BuilderOrder products(List<MerchantProduct> products){
            this.products = products;
            return this;
        }

        public BuilderOrder orderDate(Date orderDate){
            this.orderDate = orderDate;
            return this;
        }
        public BuilderOrder media(Media media){
            this.media = media;
            return this;
        }

        public Order build(){
            Order res = new Order();
            res.setAmount(amount);
            res.setCurrentCode(currentCode);
            res.setMedia(media);
            res.setOrderDate(orderDate);
            res.setProducts(products);
            res.setReferenceMedia(referenceMedia);
            return res;
        }
    }


}
