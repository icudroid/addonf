package fr.k2i.adbeback.webapp.bean.configure;

import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.webapp.bean.configure.information.Information;
import fr.k2i.adbeback.webapp.bean.configure.url.Url;
import lombok.Data;

import java.util.List;

/**
 * User: dimitri
 * Date: 24/03/14
 * Time: 17:01
 * Goal:
 */
@Data
public class PaymentConfigure{
    private double amount;
    private String currencyCode;
    private String idPartner;
    private String idTransaction;
    private Information informations;
    private String callSysUrl;
    private String callBackUrl;
    private Boolean selfAd;//utilise ses propres pub
    private MediaType mediaType;
    private Boolean showSplashScreen;
}
