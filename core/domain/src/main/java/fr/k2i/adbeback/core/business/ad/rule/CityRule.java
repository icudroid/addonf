package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.CITY_RULE)
public class CityRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;

    @Column(name = IMetaData.ColumnMetadata.AdRule.CITY)
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityRule)) return false;
        if (!super.equals(o)) return false;

        CityRule cityRule = (CityRule) o;

        if (city != null ? !city.equals(cityRule.city) : cityRule.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "CityRule{" +
                "city='" + city + '\'' +
                '}';
    }
}
