package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: dimitri
 * Date: 05/12/13
 * Time: 16:17
 * Goal:
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ad_user")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class User extends BaseObject implements Serializable {

    @Enumerated(EnumType.STRING)
    protected Sex sex;

    @Column(nullable = true, length = 50)
    protected String firstname;

    @Column(nullable = true, length = 50)
    protected String lastname;


    @Id
    @SequenceGenerator(name = "User_Gen", sequenceName = "User_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_Gen")
    protected Long id;
    @Column(nullable = false, length = 50, unique = true)
    protected String username;                    // required
    @Column(nullable = false)
    protected String password;                    // required

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    protected String phone;

    @Embedded
    protected Address address = new Address();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_role",
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
    @Version
    protected Integer version;

   /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", credentialsExpired=" + credentialsExpired +
                ", version=" + version +
                '}';
    }
}
