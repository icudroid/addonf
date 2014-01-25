package fr.k2i.adbeback.core.business.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;


import fr.k2i.adbeback.core.business.ad.ViewedAd;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.LabelValue;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.goosegame.GooseToken;
import fr.k2i.adbeback.core.business.goosegame.GooseWin;

/**
 * This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "player")
@Data
public class Player extends BaseObject implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;                    // required
    @Column(nullable = false)
    private String password;                    // required
    @Transient
    private String confirmPassword;

    @Column(name = "first_name", nullable = true, length = 50)
    private String firstName;                   // required

    @Column(name = "last_name", nullable = true, length = 50)
    private String lastName;                    // required

    @Column(nullable = false, unique = true)
    private String email;                       // required; unique

    @Column(name = "phone_number")
    private String phoneNumber;

    private String website;

    @Embedded
    private Address address = new Address();

    @Version
    private Integer version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();

    @Column(name = "account_enabled")
    private boolean enabled;

    @Column(name = "account_expired", nullable = false)
    private boolean accountExpired;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked;

    @Column(name = "credentials_expired", nullable = false)
    private boolean credentialsExpired;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Boolean newsletter;

    @OneToMany(mappedBy="player",cascade=CascadeType.ALL)
    private List<AbstractAdGame> games = new ArrayList<AbstractAdGame>();

    @OneToMany(cascade=CascadeType.ALL,mappedBy="player")
    @OrderBy("windate DESC")
    private List<GooseWin> wins = new ArrayList<GooseWin>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="PLAYER_ID")
    private List<GooseToken> gooseTokens;

    private Integer validatedLevel;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="player")
    private List<ViewedAd> viewedAds = new ArrayList<ViewedAd>();

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

	/**
     * Default constructor - creates a new instance with no values set.
     */
    public Player() {
    }

    /**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public Player(final String username) {
        this.username = username;
    }

    @Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     *
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getName(), role.getName()));
            }
        }

        return userRoles;
    }

    /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll(roles);
        return authorities;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }


    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public void addGooseToken(GooseToken gooseToken) {
        if(gooseTokens==null){
            gooseTokens = new ArrayList<GooseToken>();
        }
        gooseToken.setPlayer(this);
        gooseTokens.add(gooseToken);
    }


    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
