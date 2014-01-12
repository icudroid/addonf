package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.country.City;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.CITY_RULE)
public class CityRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.CITY_JOIN)
    private City city;


    @Column(name = IMetaData.ColumnMetadata.AdRule.AROUND)
    private Integer around;


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
