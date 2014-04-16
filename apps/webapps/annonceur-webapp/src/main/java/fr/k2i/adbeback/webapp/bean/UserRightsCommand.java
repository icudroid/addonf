package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.user.AdRight;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * User: dimitri
 * Date: 15/04/14
 * Time: 17:39
 * Goal:
 */
@Data
public class UserRightsCommand implements Serializable{
    private List<UserRight> rights;
}
