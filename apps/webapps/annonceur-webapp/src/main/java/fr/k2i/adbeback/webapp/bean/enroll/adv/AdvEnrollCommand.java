package fr.k2i.adbeback.webapp.bean.enroll.adv;

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
public class AdvEnrollCommand implements Serializable {
    private InformationCommand info;
    private AdvUserBean user;
    private CustomizeCommand customize;
    private AttachementsCommand attachements;

    public AdvEnrollCommand(){
        info = new InformationCommand();
        attachements = new AttachementsCommand();
        user = new AdvUserBean();
        customize = new CustomizeCommand();

    }

}
