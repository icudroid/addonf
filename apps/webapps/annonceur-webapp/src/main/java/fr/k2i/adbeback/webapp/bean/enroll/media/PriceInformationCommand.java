package fr.k2i.adbeback.webapp.bean.enroll.media;

import fr.k2i.adbeback.core.business.user.MediaType;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 12/04/14
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
@Data
public class PriceInformationCommand  implements Serializable{
    private Map<MediaType,List<CategoryPriceBean>> prices = new HashMap<MediaType, List<CategoryPriceBean>>();
    private CategoryPriceBean current = new CategoryPriceBean();
}

