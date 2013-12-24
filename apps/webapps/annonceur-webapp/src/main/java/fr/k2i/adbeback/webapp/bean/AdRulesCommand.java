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
    private List<CountryRule> countryRules = new ArrayList<CountryRule>();
    private List<CityRule> cityRules = new ArrayList<CityRule>();
    private SexRule sexRule;
    private AgeRule ageRule;
}
