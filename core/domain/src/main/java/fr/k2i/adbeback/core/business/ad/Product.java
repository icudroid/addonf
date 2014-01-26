package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = IMetaData.TableMetadata.PRODUCT)
public class Product extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8346229795953025008L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.Product.ID)
	private Long id;

    @Column(name = IMetaData.ColumnMetadata.Product.NAME)
	private String name;

    @Column(name = IMetaData.ColumnMetadata.Product.DESC)
	private String description;

    @Column(name = IMetaData.ColumnMetadata.Product.PUBLIC_FEE)
	private Double publicPrice;

    @Column(name = IMetaData.ColumnMetadata.Product.AD_FEE)
	private Integer adPrice;

    @Column(name = IMetaData.ColumnMetadata.Product.LOGO)
	private String logo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Double publicPrice) {
		this.publicPrice = publicPrice;
	}

	public Integer getAdPrice() {
		return adPrice;
	}

	public void setAdPrice(Integer adPrice) {
		this.adPrice = adPrice;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adPrice == null) ? 0 : adPrice.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((publicPrice == null) ? 0 : publicPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (adPrice == null) {
			if (other.adPrice != null)
				return false;
		} else if (!adPrice.equals(other.adPrice))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (publicPrice == null) {
			if (other.publicPrice != null)
				return false;
		} else if (!publicPrice.equals(other.publicPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description="
				+ description + ", publicPrice=" + publicPrice + ", adPrice="
				+ adPrice + "]";
	}

}
