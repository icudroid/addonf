package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
@Data
public class CampaignCommand implements Serializable{
    private InformationCommand information = new InformationCommand();
    private ProductBean product = new ProductBean();
    private AdRulesCommand rules = new AdRulesCommand();
    private AdService adServices = new AdService();


}
