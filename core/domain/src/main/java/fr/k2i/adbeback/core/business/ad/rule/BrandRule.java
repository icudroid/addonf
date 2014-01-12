package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.BRAND_RULE)
public class BrandRule extends AdService {
	private static final long serialVersionUID = 2387046492593489427L;


    @Override
    public String toString() {
        return "Brand";
    }
}
