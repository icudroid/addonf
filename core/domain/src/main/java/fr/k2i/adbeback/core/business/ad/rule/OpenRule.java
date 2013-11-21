package fr.k2i.adbeback.core.business.ad.rule;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
@DiscriminatorValue("Open")
public class OpenRule extends AdRule {
	private static final long serialVersionUID = 2723929702129656644L;
	private List<AdResponse>responses;
	private AdResponse correct;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CORRECT_ID")
	public AdResponse getCorrect() {
		return correct;
	}
	public void setCorrect(AdResponse correct) {
		this.correct = correct;
	}
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "RULE_ID")
	public List<AdResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<AdResponse> responses) {
		this.responses = responses;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpenRule)) return false;
        if (!super.equals(o)) return false;

        OpenRule openRule = (OpenRule) o;

        if (correct != null ? !correct.equals(openRule.correct) : openRule.correct != null) return false;
        if (responses != null ? !responses.equals(openRule.responses) : openRule.responses != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (responses != null ? responses.hashCode() : 0);
        result = 31 * result + (correct != null ? correct.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpenRule{" +
                "responses=" + responses +
                ", correct=" + correct +
                '}';
    }
}
