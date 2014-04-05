package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * User: dimitri
 * Date: 05/12/13
 * Time: 16:21
 * Goal:
 */
@Data
@Entity
@DiscriminatorValue("Brand")
public class BrandUser extends User{

    @ManyToOne
    @JoinColumn(name= IMetaData.ColumnMetadata.User.BRAND_JOIN)
    private Brand brand;


    @OneToMany
    @JoinColumn(name = IMetaData.ColumnMetadata.User.AD_JOIN)
    private List<Ad> inChargeOf;

}
