package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.user.CategoryPrice;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.webapp.bean.DisplayOnMediasBean;
import fr.k2i.adbeback.webapp.bean.MediaBean;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: dimitri
 * Date: 17/04/14
 * Time: 15:46
 * Goal:
 */
@Service
public class AdCampaignFacade {


    @Autowired
    private IMediaDao mediaDao;


    @Transactional
    public DisplayOnMediasBean loadMediasBid(){
        DisplayOnMediasBean displayOnMediasBean = new DisplayOnMediasBean();

        Map<MediaBean, Map<MediaType, List<CategoryPriceBean>>> displays = new HashMap<MediaBean, Map<MediaType, List<CategoryPriceBean>>>();
        displayOnMediasBean.setDisplays(displays);
        for (Media media : mediaDao.getAll()) {
            MediaBean mediaBean = new MediaBean(media.getId(), media.getName());
            displays.put(mediaBean, new HashMap<MediaType, List<CategoryPriceBean>>());

            List<CategoryPrice> minPriceByMediaType = media.getMinPriceByMediaType();
            for (CategoryPrice categoryPrice : minPriceByMediaType) {
                MediaType mediaType = categoryPrice.getMediaType();
                Map<MediaType, List<CategoryPriceBean>> mediaTypeListMap = displays.get(mediaType);

                if(mediaTypeListMap == null){
                    mediaTypeListMap  = new HashMap<MediaType, List<CategoryPriceBean>>();
                    mediaTypeListMap.put(mediaType,new ArrayList<CategoryPriceBean>());
                }

                List<CategoryPriceBean> categoryPriceBeans = mediaTypeListMap.get(mediaType);

                CategoryPriceBean cp = new CategoryPriceBean();

                cp.setCategory(categoryPrice.getCategory().getKey());
                cp.setMediaType(mediaType);
                cp.setPrice(categoryPrice.getMinPrice());

                categoryPriceBeans.add(cp);

            }

        }

        return displayOnMediasBean;
    }
}
