package fr.k2i.adbeback.core.business.ad.rule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.country.Country;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = IMetaData.TableMetadata.AD_RULE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.AdRule.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract class  AdRule extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8629354590055381187L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

    @Column(name = IMetaData.ColumnMetadata.AdRule.QUESTION)
	protected String question;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdRule)) return false;

        AdRule adRule = (AdRule) o;

        if (id != null ? !id.equals(adRule.id) : adRule.id != null) return false;
        if (question != null ? !question.equals(adRule.question) : adRule.question != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }
}
