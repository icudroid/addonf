package fr.k2i.adbeback.core.business.player;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
@Data
public class WebRole implements Serializable, GrantedAuthority {

    private Role role;

    public WebRole(){}
    public WebRole(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
