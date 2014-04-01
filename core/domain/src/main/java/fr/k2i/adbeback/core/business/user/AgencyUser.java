package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.IMetaData;
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
@DiscriminatorValue("Agency")
public class AgencyUser extends User{

    @ManyToOne
    @JoinColumn(name= IMetaData.ColumnMetadata.User.AGENCY_JOIN)
    private Agency agency;


    @ManyToMany
    @JoinTable(
            name = IMetaData.ColumnMetadata.User.IN_CHARGE_OF_TABLE_JOIN,
            joinColumns = { @JoinColumn(name = IMetaData.ColumnMetadata.User.AGENCY_JOIN) },
            inverseJoinColumns = @JoinColumn(name = IMetaData.ColumnMetadata.User.BRAND_JOIN)
    )
    private List<Brand> inChargeOf;

}
