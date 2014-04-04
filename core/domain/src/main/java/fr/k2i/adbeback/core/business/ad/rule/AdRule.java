package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = IMetaData.TableMetadata.AD_RULE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.AdRule.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract class  AdRule extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8629354590055381187L;

    @Id
    @SequenceGenerator(name = "AdRule_Gen", sequenceName = "AdRule_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdRule_Gen")
	protected Long id;

}
