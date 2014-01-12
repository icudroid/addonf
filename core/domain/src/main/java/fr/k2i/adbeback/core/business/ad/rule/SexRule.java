package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.SEX_RULE)
public class SexRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;

    @Enumerated(EnumType.STRING)
    @Column(name = IMetaData.ColumnMetadata.AdRule.SEX)
    private Sex sex;

    @Override
    public String toString() {
        return "SexRule{" +
                "sex=" + sex +
                '}';
    }
}
