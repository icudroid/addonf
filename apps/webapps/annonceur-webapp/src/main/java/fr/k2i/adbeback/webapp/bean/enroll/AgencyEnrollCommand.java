package fr.k2i.adbeback.webapp.bean.enroll;

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
    private AgencyInformationCommand info;
    private AgencyUsersCommand users;
    private AttachementsCommand attachements;

}
