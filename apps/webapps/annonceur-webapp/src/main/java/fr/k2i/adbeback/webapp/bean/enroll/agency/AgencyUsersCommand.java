package fr.k2i.adbeback.webapp.bean.enroll.agency;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 11:18
 * Goal:
 */
@Data
public class AgencyUsersCommand implements Serializable{
    private List<AgencyUserBean> users = new ArrayList<AgencyUserBean>();
    private AgencyUserBean current = new AgencyUserBean();
}
