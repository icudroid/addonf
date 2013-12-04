package fr.k2i.adbeback.core.business.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;

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
