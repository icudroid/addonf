package fr.k2i.adbeback.core.business.ad;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = IMetaData.TableMetadata.BRAND)
public class Brand extends BaseObject implements Serializable ,UserDetails{
	private static final long serialVersionUID = -2695302801414355764L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.Brand.ID)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.PRODUCT_JOIN)
    private List<Product> products;

    @Column(name = IMetaData.ColumnMetadata.Brand.NAME)
    private String name;

    @Column(name = IMetaData.ColumnMetadata.Brand.LOGO)
	private String logo;

    @OneToMany(mappedBy = "brand",cascade = {CascadeType.ALL})
    private List<Contact> contacts = new ArrayList<Contact>();

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.MAIN_CONTACT)
    private Contact main;

    @Column(name = IMetaData.ColumnMetadata.Brand.SIRET)
    private String siret;

    @Embedded
    private Address address;

    @Column(name = IMetaData.ColumnMetadata.Brand.PWD)
    private String password;

    @Column(name = IMetaData.ColumnMetadata.Brand.LOGIN, nullable = false, unique = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = IMetaData.ColumnMetadata.Brand.ROLE_TABLE_JOIN,
            joinColumns = { @JoinColumn(name = IMetaData.ColumnMetadata.Brand.BRAND_JOIN) },
            inverseJoinColumns = @JoinColumn(name = IMetaData.ColumnMetadata.Brand.ROLE_JOIN)
    )
    private  Set<Role> roles = new HashSet<Role>();
    private  boolean enabled;
    private  boolean accountExpired;
    private  boolean accountLocked;
    private  boolean credentialsExpired;
    private  Integer version;



    public Set<Role> getRoles() {
        return roles;
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

    @Version
    public Integer getVersion() {
        return version;
    }

    @Column(name = "account_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Column(name = "account_expired", nullable = false)
    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Column(name = "account_locked", nullable = false)
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Column(name = "credentials_expired", nullable = false)
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact getMain() {
        return main;
    }

    public void setMain(Contact main) {
        if(!contacts.contains(main)){
            contacts.add(main);
            main.setBrand(this);
        }
        this.main = main;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;

        Brand brand = (Brand) o;

        if (email != null ? !email.equals(brand.email) : brand.email != null) return false;
        if (name != null ? !name.equals(brand.name) : brand.name != null) return false;
        if (siret != null ? !siret.equals(brand.siret) : brand.siret != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (siret != null ? siret.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
