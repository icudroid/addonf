package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 19/09/14
 * Time: 16:24
 * Goal:
 */
@Data
public class PlayerForm implements Serializable{
    private Sex sex;
    private String username;
    private String email;
    private Date birthday;
    private Long cityId;
    private String password;
    private boolean newsletter;
}
