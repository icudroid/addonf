package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Sex;

import javax.persistence.*;


@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.SEX_RULE)
public class SexRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;

    @Enumerated(EnumType.STRING)
    @Column(name = IMetaData.ColumnMetadata.AdRule.SEX)
    private Sex sex;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "SexRule{" +
                "sex=" + sex +
                '}';
    }
}
