package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.core.business.ad.rule.ProductRule;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
@Data
public class AdService implements Serializable{
    private List<BrandRule> brandRules = new ArrayList<BrandRule>();
    private List<OpenRule> openRules = new ArrayList<OpenRule>();
    private List<ProductRule> productRules = new ArrayList<ProductRule>();
}
