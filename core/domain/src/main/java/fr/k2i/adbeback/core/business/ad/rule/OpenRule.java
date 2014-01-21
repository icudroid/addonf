package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.OPEN_RULE)
public class OpenRule extends AdService {
	private static final long serialVersionUID = 2723929702129656644L;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.RESPONSE_JOIN)
	private List<AdResponse>responses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.CORRECT_RESPONSE)
    private AdResponse correct;


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

    public void addResponse(AdResponse adResponse) {
        if(responses == null){
            responses = new ArrayList<AdResponse>();
        }
        responses.add(adResponse);
    }
}
