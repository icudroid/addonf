package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.company.Company;
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
@PrimaryKeyJoinColumn(name="company_id")
public class Brand extends Company{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.PRODUCT_JOIN)
    private List<Product> products;

    @Column(name = IMetaData.ColumnMetadata.Brand.LOGO)
	private String logo;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Brand.USER_JOIN)
    private BrandUser user;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;

        Brand brand = (Brand) o;

        if (name != null ? !name.equals(brand.name) : brand.name != null) return false;
        if (siret != null ? !siret.equals(brand.siret) : brand.siret != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (siret != null ? siret.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                '}';
    }
}
