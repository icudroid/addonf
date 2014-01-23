package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.CountryRule;
import fr.k2i.adbeback.core.business.country.Country;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 23/01/14
 * Time: 16:37
 * Goal:
 */
@Data
public class CountryRuleBean  extends AdRuleBean {
    private CountryBean country;

    public CountryRuleBean(){}
    public CountryRuleBean(CountryRule adRule) {
        this.id = adRule.getId();
        country = new CountryBean(adRule.getCountry());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityRuleBean)) return false;
        if (!super.equals(o)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}
