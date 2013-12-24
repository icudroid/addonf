package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AmountRule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class AdBean implements Serializable{
    private Long id;
    private Double initialAmount;
    private Double leftAmount;
    private AdType adType;
    private Date startDate;
    private Date endDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Double getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Double leftAmount) {
        this.leftAmount = leftAmount;
    }

    public AdType getAdType() {
        return adType;
    }

    public void setAdType(AdType adType) {
        this.adType = adType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AdBean(){}
    public AdBean(Ad ad) {
        id = ad.getId();
        initialAmount =ad.getInitialAmount();
        adType = ad.getType();
        startDate = ad.getStartDate();
        endDate = ad.getEndDate();

        List<AdRule> rules = ad.getRules();
        for (AdRule rule : rules) {
            if(rule instanceof AmountRule){
                leftAmount = ((AmountRule) rule).getAmount();
                break;
            }
        }

    }
}
