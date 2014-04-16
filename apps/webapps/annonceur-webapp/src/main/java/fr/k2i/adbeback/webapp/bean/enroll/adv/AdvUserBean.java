package fr.k2i.adbeback.webapp.bean.enroll.adv;

import fr.k2i.adbeback.webapp.bean.UserBean;
import lombok.Data;

@Data
public class AdvUserBean extends UserBean {
    private String password;
    private String passwordConfirm;
}
