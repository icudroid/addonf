package fr.k2i.adbeback.webapp.bean.adservice;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.ad.rule.AdResponse;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 21/01/14
 * Time: 10:17
 * Goal:
 */
@Data
public class OpenRuleBean extends  AdServiceBean implements Serializable {
    private List<AdResponseBean> responses;
    private String question;

    public OpenRuleBean(){
        this(true);
    }

    public OpenRuleBean(boolean createDefault){
        if(createDefault){
            responses = Lists.newArrayList(new AdResponseBean(),new AdResponseBean(),new AdResponseBean());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpenRuleBean)) return false;
        if (!super.equals(o)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void addResponse(AdResponse response,boolean isCorrect,String base) throws IOException {
        if(responses == null){
            responses = new ArrayList<AdResponseBean>();
        }
        AdResponseBean bean = new AdResponseBean();

        bean.setImage(new FileCommand(new File(base+response.getImage())));
        bean.setCorrect(isCorrect);
        bean.setResponse(response.getResponse());
        bean.setId(response.getId());

        responses.add(bean);
    }
}
