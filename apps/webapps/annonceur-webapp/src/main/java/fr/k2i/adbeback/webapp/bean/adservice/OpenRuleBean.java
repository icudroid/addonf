package fr.k2i.adbeback.webapp.bean.adservice;

import fr.k2i.adbeback.core.business.ad.rule.AdResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * User: dimitri
 * Date: 21/01/14
 * Time: 10:17
 * Goal:
 */
@Data
public class OpenRuleBean extends  AdServiceBean implements Serializable {
    private List<AdResponseBean> responses;
    private String question;
}
