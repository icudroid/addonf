package fr.k2i.adbeback.core.business.player;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Transient;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
@Data
public class WebPlayer implements UserDetails {
    private Player user;

    /**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new WebRole(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !user.isAccountExpired();
    }


    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !user.isAccountLocked();
    }


    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !user.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public WebPlayer(){}


    public WebPlayer(Player user){
        this.user = user;
    }

}
