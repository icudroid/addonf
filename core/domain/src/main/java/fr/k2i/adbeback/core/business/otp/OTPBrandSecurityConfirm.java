package fr.k2i.adbeback.core.business.otp;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.Brand;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * User: dimitri
 * Date: 18/02/13
 * Time: 11:29
 * Goal:
 */
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.OTPSecurity.Discrimator.BRAND)
public class OTPBrandSecurityConfirm extends OTPSecurity {


    @ManyToOne
    @JoinColumn(name=IMetaData.ColumnMetadata.OTPSecurity.BRAND_JOIN)
    protected Brand brand;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OTPBrandSecurityConfirm)) return false;

        OTPBrandSecurityConfirm that = (OTPBrandSecurityConfirm) o;

        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return brand != null ? brand.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "OTPBrandSecurityConfirm{" +
                "brand=" + brand +
                '}';
    }
}
