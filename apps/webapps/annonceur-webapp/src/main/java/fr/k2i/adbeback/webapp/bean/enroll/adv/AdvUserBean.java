package fr.k2i.adbeback.webapp.bean.enroll.adv;

import fr.k2i.adbeback.webapp.bean.ContactBean;
import fr.k2i.adbeback.webapp.bean.enroll.agency.AgencyRole;
import lombok.Data;

@Data
public class AdvUserBean extends ContactBean {
    private String password;
    private String passwordConfirm;
}
