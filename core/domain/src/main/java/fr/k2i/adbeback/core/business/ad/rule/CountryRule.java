package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.country.Country;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.COUNTRY_RULE)
public class CountryRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;

    @ManyToOne()
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.JOIN_COUNTRY)
    private Country country;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryRule)) return false;
        if (!super.equals(o)) return false;

        CountryRule that = (CountryRule) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "CountryRule{" +
                "country=" + country +
                '}';
    }
}
