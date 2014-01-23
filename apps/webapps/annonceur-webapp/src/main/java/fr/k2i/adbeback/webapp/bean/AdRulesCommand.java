package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.rule.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
@Data
public class AdRulesCommand implements Serializable{
    private List<CountryRuleBean> countryRules = new ArrayList<CountryRuleBean>();
    private List<CityRuleBean> cityRules = new ArrayList<CityRuleBean>();
    private SexRuleBean sexRule;
    private AgeRuleBean ageRule;

    public void addRule(CityRule adRule) {
        if(cityRules == null){
            cityRules = new ArrayList<CityRuleBean>();
        }
        cityRules.add(new CityRuleBean(adRule));
    }

    public void addRule(CountryRule adRule) {
        if(countryRules == null){
            countryRules = new ArrayList<CountryRuleBean>();
        }
        countryRules.add(new CountryRuleBean(adRule));
    }

    public void addRule(SexRule adRule) {
        sexRule = new SexRuleBean(adRule);
    }

    public void addRule(AgeRule adRule) {
        ageRule = new AgeRuleBean(adRule);
    }

    public void addRule(AdRule adRule) {
        if(adRule instanceof CityRule){
            addRule((CityRule)adRule);
        }else if(adRule instanceof CountryRule){
            addRule((CountryRule)adRule);
        }else if(adRule instanceof SexRule){
            addRule((SexRule)adRule);
        }else if(adRule instanceof AgeRule){
            addRule((AgeRule)adRule);
        }
    }



}
