package fr.k2i.adbeback.core.business.ad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import fr.k2i.adbeback.core.business.BaseObject;

@Entity
@Table(name = "brand")
public class Brand extends BaseObject implements Serializable {
	private static final long serialVersionUID = -2695302801414355764L;
	private List<Product> products;
	private Long id;
	private String name;
	private String logo;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "BRAND_ID")
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
