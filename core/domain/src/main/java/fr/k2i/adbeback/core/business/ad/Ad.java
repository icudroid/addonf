package fr.k2i.adbeback.core.business.ad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.country.Country;

@Entity
@Table(name = IMetaData.TableMetadata.AD)
public class Ad extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8627592656680311906L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.Ad.ID)
    private Long id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.BRAND)
    private Brand brand;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.PRODUCT)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.RULE_JOIN)
    private List<AdRule> rules;

    @Column(name = IMetaData.ColumnMetadata.Ad.VIDEO)
	private String video;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = IMetaData.ColumnMetadata.Ad.TYPE)
	private AdType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<AdRule> getRules() {
		return rules;
	}

	public void setRules(List<AdRule> rules) {
		this.rules = rules;
	}
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}


	public AdType getType() {
		return type;
	}

	public void setType(AdType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		Ad other = (Ad) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ad [id=" + id + ", brand=" + brand.getName() + ", product=" + product.getName()
				+ "]";
	}

}
