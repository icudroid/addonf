package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
