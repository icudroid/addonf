package fr.k2i.adbeback.core.business.transaction;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;
import lombok.experimental.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Currency;

/**
 * User: dimitri
 * Date: 25/09/14
 * Time: 14:20
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.MERCHANT_PRODUCT)
public class MerchantProduct extends BaseObject implements Serializable {
    @Id
    @SequenceGenerator(name = "MerchantProduct_Gen", sequenceName = "MerchantProduct_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MerchantProduct_Gen")
    private Long id;

    private String product;

    private Integer nb;


    public static  MerchantProductBuilder builder(){
        return new MerchantProductBuilder();
    }
    public static class MerchantProductBuilder{
        private Long id;
        private String product;
        private Integer nb;

        MerchantProductBuilder(){

        }

        public MerchantProductBuilder id(Long id){
            this.id = id;
            return this;
        }
        public MerchantProductBuilder product(String product){
            this.product = product;
            return this;
        }
        public MerchantProductBuilder nb(Integer nb){
            this.nb = nb;
            return this;
        }

        public MerchantProduct build(){
            MerchantProduct res = new MerchantProduct();
            res.setId(id);
            res.setNb(nb);
            res.setProduct(product);
            return res;
        }
    }
}
