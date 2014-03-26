package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.MULTI_RESPONSE_RULE)
public class MultiResponseRule extends AdService {
	private static final long serialVersionUID = 2723929702129656644L;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.RESPONSE_JOIN)
	private List<AdResponse>responses;

    private String addonText;

    private String BtnValidText;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.RESPONSE_CORRECT_JOIN)
    private List<AdResponse>corrects;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiResponseRule)) return false;
        if (!super.equals(o)) return false;

        MultiResponseRule openRule = (MultiResponseRule) o;

        if (responses != null ? !responses.equals(openRule.responses) : openRule.responses != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (responses != null ? responses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpenRule{" +
                "responses=" + responses +
                '}';
    }

    public void addResponse(AdResponse adResponse) {
        if(responses == null){
            responses = new ArrayList<AdResponse>();
        }
        responses.add(adResponse);
    }
}
