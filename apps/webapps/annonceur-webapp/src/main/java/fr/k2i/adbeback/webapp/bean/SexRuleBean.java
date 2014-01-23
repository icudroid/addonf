package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.SexRule;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

/**
 * User: dimitri
 * Date: 23/01/14
 * Time: 16:37
 * Goal:
 */
@Data
public class SexRuleBean extends AdRuleBean{
    private Sex sex;

    public SexRuleBean(){}
    public SexRuleBean(SexRule adRule) {
        id = adRule.getId();
        sex= adRule.getSex();
    }

    public SexRule toSexRule() {
        SexRule sexRule = new SexRule();
        //sexRule.setId(id);
        sexRule.setSex(sex);
        return sexRule;
    }


}
