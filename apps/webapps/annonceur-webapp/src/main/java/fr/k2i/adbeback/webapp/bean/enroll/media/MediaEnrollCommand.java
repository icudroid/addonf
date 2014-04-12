package fr.k2i.adbeback.webapp.bean.enroll.media;

import fr.k2i.adbeback.webapp.bean.enroll.AttachementsCommand;
import fr.k2i.adbeback.webapp.bean.enroll.InformationCommand;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 11:13
 * Goal:
 */
@Data
public class MediaEnrollCommand implements Serializable {
    private InformationCommand info;
    private MediaUserBean user;
    private PriceInformationCommand prices;
    private AttachementsCommand attachements;


    public MediaEnrollCommand(){
        info = new InformationCommand();
        attachements = new AttachementsCommand();
        user = new MediaUserBean();
        prices = new PriceInformationCommand();

    }

}
