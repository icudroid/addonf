package fr.k2i.adbeback.core.business.ad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Address;

@Entity
@Table(name = IMetaData.TableMetadata.BRAND)
public class Brand extends BaseObject implements Serializable {
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

    @OneToMany(mappedBy = IMetaData.ColumnMetadata.Brand.CONTACT_JOIN,cascade = {CascadeType.ALL})
    private List<Contact> contacts;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.MAIN_CONTACT)
    private Contact main;

    @Column(name = IMetaData.ColumnMetadata.Brand.TVA_NUMBER)
    private String tvaNumber;

    @Column(name = IMetaData.ColumnMetadata.Brand.SIRET)
    private String siret;

    @Embedded
    private Address address;

    @Column(name = IMetaData.ColumnMetadata.Brand.PWD)
    private String password;

    @Column(name = IMetaData.ColumnMetadata.Brand.PHONE)
    private String phone;

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
        this.main = main;
    }

    public String getTvaNumber() {
        return tvaNumber;
    }

    public void setTvaNumber(String tvaNumber) {
        this.tvaNumber = tvaNumber;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;

        Brand brand = (Brand) o;

        if (!id.equals(brand.id)) return false;
        if (!logo.equals(brand.logo)) return false;
        if (!name.equals(brand.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + logo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Brand [products=" + products + ", id=" + id + "]";
    }

}
