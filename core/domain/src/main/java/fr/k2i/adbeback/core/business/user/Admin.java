package fr.k2i.adbeback.core.business.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: dimitri
 * Date: 05/12/13
 * Time: 16:21
 * Goal:
 */
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{

}
