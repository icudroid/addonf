package fr.k2i.adbeback.core.business.ad.rule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.country.Country;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = IMetaData.TableMetadata.AD_RULE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.AdRule.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract class  AdRule extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8629354590055381187L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

}
