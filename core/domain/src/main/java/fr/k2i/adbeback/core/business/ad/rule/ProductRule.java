package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.PRODUCT_RULE)
public class ProductRule extends AdService {
	private static final long serialVersionUID = 6708314171621564778L;

	@Override
	public String toString() {
		return "ProductRule";
	}

}
