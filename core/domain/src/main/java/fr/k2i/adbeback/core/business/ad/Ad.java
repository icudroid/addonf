package fr.k2i.adbeback.core.business.ad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AmountRule;
import fr.k2i.adbeback.core.business.country.Country;
import lombok.Data;

@Data
@Entity
@Table(name = IMetaData.TableMetadata.AD)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.Ad.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract  class Ad extends BaseObject implements Serializable {
	protected static final long serialVersionUID = -8627592656680311906L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.Ad.ID)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.BRAND)
    protected Brand brand;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.PRODUCT)
    protected Product product;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.RULE_JOIN)
    protected List<AdRule> rules;

    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.INITIAL_AMOUNT)
    protected Double initialAmount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = IMetaData.ColumnMetadata.Ad.TYPE)
	protected AdType type;


    @Temporal(TemporalType.DATE)
    @Column(name = IMetaData.ColumnMetadata.Ad.START_DATE)
    protected Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = IMetaData.ColumnMetadata.Ad.END_DATE)
    protected Date endDate;

    @Column(name = IMetaData.ColumnMetadata.Ad.DURATION)
    protected Long duration;

    @Column(name = IMetaData.ColumnMetadata.Ad.AD_FILE)
    protected String adFile;

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


    public <ADRULE extends AdRule> List<ADRULE> getRules(Class<ADRULE> clazz) {
        List<ADRULE> res = new ArrayList<ADRULE>();
        for (AdRule rule : rules) {
            if(rule.getClass().equals(clazz)){
                res.add((ADRULE)rule);
            }
        }
        return res;
    }

}
