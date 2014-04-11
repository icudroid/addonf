package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.user.Attachement;
import fr.k2i.adbeback.core.business.user.BrandUser;
import fr.k2i.adbeback.core.business.user.LegalStatus;
import fr.k2i.adbeback.core.business.user.MediaType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = IMetaData.TableMetadata.BRAND)
public class Brand extends BaseObject implements Serializable{
	private static final long serialVersionUID = -2695302801414355764L;

    @Id
    @SequenceGenerator(name = "Brand_Gen", sequenceName = "Brand_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Brand_Gen")
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

    @Column(name = IMetaData.ColumnMetadata.Brand.LOGIN, nullable = false, unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date CreatedDate;

    @Enumerated(EnumType.STRING)
    private LegalStatus legalStatus;

    private String phone;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.USER_JOIN)
    private BrandUser user;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name=IMetaData.TableMetadata.ATTACHEMENTS)
    @MapKeyColumn(name=IMetaData.ColumnMetadata.Attachement.ID,length = 32)
    private Map<String,Attachement> attachements;


    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.TYPE)
    protected Sector sector;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.JOIN)
    protected List<CustomerTarget> targetCustomers;

    @ElementCollection
    @CollectionTable(name = "target_media", joinColumns = @JoinColumn(name = "brand_id"))
    @Column(name = "target_media")
    @Enumerated(EnumType.STRING)
    protected List<MediaType> targetMedia;

    @ElementCollection
    @CollectionTable(name = "product_line", joinColumns = @JoinColumn(name = "brand_id"))
    @Column(name = "product_line")
    protected List<String> productLines;

    public void setMain(Contact main) {
        if(!contacts.contains(main)){
            contacts.add(main);
            main.setBrand(this);
        }
        this.main = main;
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
