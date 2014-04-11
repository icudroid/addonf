package fr.k2i.adbeback.webapp.bean.enroll.agency;

import fr.k2i.adbeback.webapp.bean.ContactBean;
import lombok.Data;

@Data
public class AgencyUserBean extends ContactBean {
    private AgencyRole role;
    private String password;
    private String passwordConfirm;
}
