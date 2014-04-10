package fr.k2i.adbeback.webapp.bean.enroll;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 10/04/14
 * Time: 15:59
 * Goal:
 */
@Data
public class UserAgencyConfirmCommand implements Serializable {
    private String email;
    private String password;
    private String passwordConfirm;

    public UserAgencyConfirmCommand(){}
    public UserAgencyConfirmCommand(String email){
        this.email = email;
    }
}
