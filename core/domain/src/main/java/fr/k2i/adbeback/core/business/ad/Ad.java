package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.user.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = IMetaData.TableMetadata.AD)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.Ad.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract  class Ad extends BaseObject implements Serializable {
	protected static final long serialVersionUID = -8627592656680311906L;

    @Id
    @SequenceGenerator(name = "Ad_Gen", sequenceName = "Ad_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Ad_Gen")
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

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.TYPE)
	protected Sector sector;

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

    @Column(name = IMetaData.ColumnMetadata.Ad.NAME)
    protected String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.PARTNER)
    protected Agency providedBy;

    @Enumerated(EnumType.STRING)
    protected AdStatus status = AdStatus.ENABLE;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.JOIN)
    protected List<UserAccess> access;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Ad.JOIN)
    private List<BidCategoryMedia> bidCategoryMedias;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ad)) return false;

        Ad ad = (Ad) o;

        if (adFile != null ? !adFile.equals(ad.adFile) : ad.adFile != null) return false;
        if (brand != null ? !brand.equals(ad.brand) : ad.brand != null) return false;
        if (name != null ? !name.equals(ad.name) : ad.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (adFile != null ? adFile.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "brand=" + brand +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    public <ADRULE extends AdRule> List<ADRULE> getRules(Class<ADRULE> clazz) {
        List<ADRULE> res = new ArrayList<ADRULE>();
        for (AdRule rule : rules) {
            if(clazz.isAssignableFrom(rule.getClass())){
                res.add((ADRULE)rule);
            }
        }
        return res;
    }

    public void addRule(AdRule rule) {
        if(rules == null){
            rules = new ArrayList<AdRule>();
        }
        rules.add(rule);
    }


}
