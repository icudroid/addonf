package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.user.AdRight;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 15/04/14
 * Time: 17:42
 * Goal:
 */
@Data
public class UserRight implements Serializable{
    private AdRight adRight;
    private UserBean user;
}
