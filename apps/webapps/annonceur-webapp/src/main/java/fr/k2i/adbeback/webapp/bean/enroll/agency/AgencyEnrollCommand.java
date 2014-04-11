package fr.k2i.adbeback.webapp.bean.enroll.agency;

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
public class AgencyEnrollCommand implements Serializable {
    private InformationCommand info;
    private AgencyUsersCommand users;
    private AttachementsCommand attachements;


    public AgencyEnrollCommand(){
        info = new InformationCommand();
        users = new AgencyUsersCommand();
        attachements = new AttachementsCommand();
    }

}
