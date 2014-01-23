package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AmountRule;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
@Data
public class AdBean implements Serializable{
    private Long id;
    private String name;
    private Double initialAmount;
    private Double leftAmount;
    private AdType adType;
    private Date startDate;
    private Date endDate;

    public AdBean(){}
    public AdBean(Ad ad) {
        id = ad.getId();
        adType = ad.getType();
        startDate = ad.getStartDate();
        endDate = ad.getEndDate();
        name = ad.getName();
        List<AmountRule> amountRules = ad.getRules(AmountRule.class);
        BigDecimal left = new BigDecimal("0.0");
        BigDecimal initial = new BigDecimal("0.0");
        for (AmountRule amountRule : amountRules) {
            left = left.add(new BigDecimal("" + amountRule.getAmount()));
            initial = initial.add(new BigDecimal("" + amountRule.getInitialAmount()));
        }
        leftAmount = left.doubleValue();
        initialAmount = initial.doubleValue();
    }
}
