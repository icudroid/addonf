package fr.k2i.adbeback.webapp.bean.enroll.agency;

import fr.k2i.adbeback.webapp.bean.UserBean;
import lombok.Data;

@Data
public class AgencyUserBean extends UserBean {
    private AgencyRole role;
    private String password;
    private String passwordConfirm;
}
