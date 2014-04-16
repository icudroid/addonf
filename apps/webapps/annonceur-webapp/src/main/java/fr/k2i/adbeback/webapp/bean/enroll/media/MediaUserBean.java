package fr.k2i.adbeback.webapp.bean.enroll.media;

import fr.k2i.adbeback.webapp.bean.UserBean;
import lombok.Data;

@Data
public class MediaUserBean extends UserBean {
    private String password;
    private String passwordConfirm;
}
