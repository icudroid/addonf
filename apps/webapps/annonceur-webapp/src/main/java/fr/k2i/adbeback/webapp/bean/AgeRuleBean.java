package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AgeRule;
import lombok.Data;

import javax.persistence.Column;

/**
 * User: dimitri
 * Date: 23/01/14
 * Time: 16:37
 * Goal:
 */
@Data
public class AgeRuleBean extends AdRuleBean {
    private Integer ageMin;
    private Integer ageMax;
    public AgeRuleBean(){}
    public AgeRuleBean(AgeRule adRule) {
        id = adRule.getId();
        ageMax = adRule.getAgeMax();
        ageMin = adRule.getAgeMin();
    }

    public AgeRule toAgeRule() {

        AgeRule ageRule = new AgeRule();
        //ageRule.setId(id);
        ageRule.setAgeMax(ageMax);
        ageRule.setAgeMin(ageMin);

        return ageRule;
    }


}
