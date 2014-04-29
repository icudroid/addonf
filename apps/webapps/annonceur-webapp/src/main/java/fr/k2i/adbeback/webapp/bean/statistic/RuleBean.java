package fr.k2i.adbeback.webapp.bean.statistic;

import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.MultiResponseRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 23/04/14
 * Time: 16:08
 * Goal:
 */
@Data
public class RuleBean implements Serializable{
    private Long id;
    private String label;
    private boolean activated;


    public RuleBean(AdService service){
        id = service.getId();
        this.activated = service.getActivated();

        if (service instanceof BrandRule) {
            BrandRule rule = (BrandRule) service;
            label = "Affiche par Logo : "+rule.getName();

        }else if (service instanceof OpenRule) {
            OpenRule rule = (OpenRule) service;
            label = "Affichage personnalisé : "+rule.getName();

        }if (service instanceof MultiResponseRule) {
            MultiResponseRule rule = (MultiResponseRule) service;
            label = "Affichage multi-réponses : "+rule.getName();
        }
    }
}
