package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.Brand;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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


    public void addInChargeOf(Brand brand){
        if(inChargeOf==null){
            inChargeOf = new ArrayList<Brand>();
        }
        inChargeOf.add(brand);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgencyUser)) return false;
        if (!super.equals(o)) return false;

        AgencyUser that = (AgencyUser) o;

        if (agency != null ? !agency.equals(that.agency) : that.agency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (agency != null ? agency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AgencyUser{" +
                "agency=" + agency.getName() +
                '}';
    }
}
