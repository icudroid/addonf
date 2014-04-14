package fr.k2i.adbeback.webapp.bean.enroll.agency;

import fr.k2i.adbeback.core.business.Constants;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 16:06
 * Goal:
 */
public enum AgencyRole {
    ADMIN(Constants.AGENCY_ADMIN_ROLE),USER(Constants.AGENCY_USER_ROLE);

    private String roleDb;
    AgencyRole(String roleDb){
        this.roleDb = roleDb;
    }

    public String getRoleDb() {
        return roleDb;
    }
}
