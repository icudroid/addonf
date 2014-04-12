package fr.k2i.adbeback.webapp.bean.enroll.media;

import fr.k2i.adbeback.webapp.bean.ContactBean;
import lombok.Data;

@Data
public class MediaUserBean extends ContactBean {
    private String password;
    private String passwordConfirm;
}
