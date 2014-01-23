package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.rule.CityRule;
import fr.k2i.adbeback.core.business.country.City;
import lombok.Data;

/**
 * User: dimitri
 * Date: 23/01/14
 * Time: 16:37
 * Goal:
 */
@Data
public class CityRuleBean extends AdRuleBean{
    private Integer around;
    private CityBean city;

    public CityRuleBean() {}

    public CityRuleBean(CityRule adRule) {
        id = adRule.getId();
        around = adRule.getAround();
        city = new CityBean(adRule.getCity());

    }

}
