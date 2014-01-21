package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.core.business.ad.rule.ProductRule;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.ProductRuleBean;
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
    private List<BrandRuleBean> brandRules = new ArrayList<BrandRuleBean>();
    private List<OpenRuleBean> openRules = new ArrayList<OpenRuleBean>();
    private List<ProductRuleBean> productRules = new ArrayList<ProductRuleBean>();
}
