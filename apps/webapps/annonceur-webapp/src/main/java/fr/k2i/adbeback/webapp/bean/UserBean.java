package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 21/12/13
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
@Data
public class UserBean implements Serializable {
    @NotNull
    private Sex sex;
    private String lastname;
    private String firstname;
    private String phone;
    @Email
    private String email;
    private String emailConfirm;

    private Long id;


}
