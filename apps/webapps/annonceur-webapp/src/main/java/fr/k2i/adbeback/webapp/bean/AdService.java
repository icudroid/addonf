package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.webapp.bean.adservice.*;
import lombok.Data;

import java.io.IOException;
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
    private List<OpenMultiRuleBean> openMultiRules = new ArrayList<OpenMultiRuleBean>();
    private List<ProductRuleBean> productRules = new ArrayList<ProductRuleBean>();


    public void addService(OpenRule adRule,String base) throws IOException {
        if(openRules == null){
            openRules = new ArrayList<OpenRuleBean>();
        }

        OpenRuleBean add = new OpenRuleBean(false);
        add.setStartDate(adRule.getStartDate());
        add.setQuestion(adRule.getQuestion());
        add.setEndDate(adRule.getEndDate());
        add.setMaxDisplayByUser(adRule.getMaxDisplayByUser());
        add.setId(adRule.getId());
        add.setName(adRule.getName());

        List<AdResponse> responses = adRule.getResponses();

        for (AdResponse response : responses) {
            add.addResponse(response, response.equals(adRule.getCorrect()), base);
        }


        openRules.add(add);
    }



    public void addService(MultiResponseRule adRule,String base) throws IOException {
        if(openMultiRules == null){
            openMultiRules = new ArrayList<OpenMultiRuleBean>();
        }

        OpenMultiRuleBean add = new OpenMultiRuleBean(false);
        add.setStartDate(adRule.getStartDate());
        add.setQuestion(adRule.getQuestion());
        add.setEndDate(adRule.getEndDate());
        add.setMaxDisplayByUser(adRule.getMaxDisplayByUser());
        add.setId(adRule.getId());
        add.setBtnValidText(adRule.getBtnValidText());
        add.setAddonText(adRule.getAddonText());
        add.setName(adRule.getName());

        List<AdResponse> responses = adRule.getResponses();


        for (AdResponse response : responses) {
            add.addResponse(response, adRule.getCorrects().contains(response), base);
        }


        openMultiRules.add(add);
    }


    public void addService(BrandRule adRule) {
        if(brandRules == null){
            brandRules = new ArrayList<BrandRuleBean>();
        }

        BrandRuleBean add = new BrandRuleBean();
        add.setStartDate(adRule.getStartDate());
        add.setEndDate(adRule.getEndDate());
        add.setMaxDisplayByUser(adRule.getMaxDisplayByUser());
        add.setId(adRule.getId());
        add.setName(adRule.getName());

        for (Brand brand : adRule.getNoDisplayWith()) {
            add.addNoDisplayWith(new BrandBean(brand));
        }

        brandRules.add(add);
    }


    public void addService(AdRule adRule, String base) throws IOException {

        if(adRule instanceof BrandRule){
            addService((BrandRule) adRule);
        }else if(adRule instanceof OpenRule){
            addService((OpenRule) adRule, base);
        }else if(adRule instanceof MultiResponseRule){
           addService((MultiResponseRule) adRule, base);
        }
    }
}
