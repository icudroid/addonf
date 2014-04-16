package fr.k2i.adbeback.webapp.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private UserRightsCommand userRights = new  UserRightsCommand();
    private InformationCommand information = new InformationCommand();
    private ProductBean product = new ProductBean();
    private AdRulesCommand rules = new AdRulesCommand();
    private AdService adServices = new AdService();
    private BrandBean brand = new BrandBean();



}
