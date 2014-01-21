package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.Brand;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.BRAND_RULE)
public class BrandRule extends AdService {
	private static final long serialVersionUID = 2387046492593489427L;

    @ManyToMany
    @JoinTable(
            name = IMetaData.ColumnMetadata.AdRule.BRAND_NO_DISPLAY_TABLE_JOIN,
            joinColumns = { @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.BRAND_RULE_JOIN) },
            inverseJoinColumns = @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.BRAND_JOIN)
    )
    private List<Brand> noDisplayWith = new ArrayList<Brand>();

    @Override
    public String toString() {
        return "Brand";
    }

    public void addNoDisplayWith(Brand brand) {
        if(noDisplayWith==null){
            noDisplayWith = new ArrayList<Brand>();
        }
        noDisplayWith.add(brand);
    }
}
