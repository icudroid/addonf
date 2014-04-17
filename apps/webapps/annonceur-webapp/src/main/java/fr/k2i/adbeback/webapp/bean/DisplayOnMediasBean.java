package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBean;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: dimitri
 * Date: 17/04/14
 * Time: 14:32
 * Goal:
 */
@Data
public class DisplayOnMediasBean implements Serializable{
    private Map<MediaBean,Map<MediaType,List<CategoryPriceBean>>> displays = new HashMap<MediaBean, Map<MediaType, List<CategoryPriceBean>>>();

}
