package fr.k2i.adbeback.core.business.player;

import java.io.Serializable;

import javax.persistence.*;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.country.City;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.country.Country;

/**
 * This class is used to represent an address with address,
 * city, province and postal-code information.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Embeddable
public class Address extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3617859655330969141L;
    private String address;
    private String compAddress;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.Address.CITY_JOIN)
    private City city;

    @Column(length = 150)
    public String getAddress() {
        return address;
    }


    @Column(length = 150)

    public String getCompAddress() {
    	return compAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                ", compAddress='" + compAddress + '\'' +
                ", city=" + city +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (address != null ? !address.equals(address1.address) : address1.address != null) return false;
        if (city != null ? !city.equals(address1.city) : address1.city != null) return false;
        if (compAddress != null ? !compAddress.equals(address1.compAddress) : address1.compAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (compAddress != null ? compAddress.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
