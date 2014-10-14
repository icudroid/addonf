package fr.k2i.adbeback.webapp.bean.configure;

import fr.k2i.adbeback.core.business.user.Category;
import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.webapp.bean.configure.information.CountryInformation;
import fr.k2i.adbeback.webapp.bean.configure.information.Information;
import fr.k2i.adbeback.webapp.bean.configure.url.Url;
import lombok.Data;
import lombok.experimental.Builder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private String category;
    private Boolean showSplashScreen;
    private Long transactionDate;
    private String validation;

    public Date getTransactionDate(){
        return new Date(transactionDate);
    }


    public static PaymentConfigure adbebackConfig(){
        PaymentConfigure res = new PaymentConfigure();

        res.setAmount(6.0*0.3);
        res.setIdPartner("1");
        res.setSelfAd(false);
        Information information = new Information();
        information.setCountry(new CountryInformation("FR"));
        res.setInformations(information);
        res.setIdTransaction(UUID.randomUUID().toString());
        res.setTransactionDate(new Date().getTime());

        return res;


    }
}
