package fr.k2i.adbeback.core.business.player;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.LabelValue;
import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.goosegame.GooseToken;
import fr.k2i.adbeback.core.business.goosegame.GooseWin;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

/**
 * This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net
 */

@Data
@Entity
@Table(name = "player")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "player")
public class Player extends BaseObject {
    @Id
    @SequenceGenerator(name = "Player_Gen", sequenceName = "Player_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Player_Gen")
    protected Long id;

    @Column(nullable = false, length = 50, unique = true)
    protected String username;                    // required
    @Column(nullable = false)
    protected String password;                    // required
    @Transient
    protected String confirmPassword;

    @Column(name = "first_name", nullable = true, length = 50)
    protected String firstName;                   // required

    @Column(name = "last_name", nullable = true, length = 50)
    protected String lastName;                    // required

    @Column(nullable = false, unique = true)
    protected String email;                       // required; unique

    @Column(name = "phone_number")
    protected String phoneNumber;

    protected String website;

    @Embedded
    protected Address address = new Address();

    @Version
    protected Integer version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<Role> roles = new HashSet<Role>();

    @Column(name = "account_enabled")
    protected boolean enabled;

    @Column(name = "account_expired", nullable = false)
    protected boolean accountExpired;

    @Column(name = "account_locked", nullable = false)
    protected boolean accountLocked;

    @Column(name = "credentials_expired", nullable = false)
    protected boolean credentialsExpired;

    @Enumerated(EnumType.STRING)
    protected Sex sex;

    @Temporal(TemporalType.DATE)
    protected Date birthday;
    protected Boolean newsletter;

    @OneToMany(mappedBy="player",cascade=CascadeType.ALL)
    protected List<AbstractAdGame> games = new ArrayList<AbstractAdGame>();

    @OneToMany(cascade=CascadeType.ALL,mappedBy="player")
    @OrderBy("windate DESC")
    protected List<GooseWin> wins = new ArrayList<GooseWin>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="PLAYER_ID")
    protected List<GooseToken> gooseTokens;

    protected Integer validatedLevel;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="player")
    protected List<ViewedAd> viewedAds = new ArrayList<ViewedAd>();

    @Enumerated(EnumType.STRING)
    protected AgeGroup ageGroup;

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
