package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.dao.ICategoryDao;
import fr.k2i.adbeback.dao.ICategoryPriceDao;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBean;
import fr.k2i.adbeback.webapp.bean.enroll.media.PriceInformationCommand;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * User: dimitri
 * Date: 05/05/14
 * Time: 13:50
 * Goal:
 */
@Service
public class MediaFacadeService {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private UserFacade userFacade;

    @Autowired
    private IMediaDao mediaDao;


    @Autowired
    private ICategoryDao categoryDao;


    @Autowired
    private ICategoryPriceDao categoryPriceDao;

    @Transactional
    public String getMediaPassPhrase() throws Exception {
        String passphrase =null;

        User currentUser = userFacade.getCurrentUser();
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);
            passphrase = media.getPassPhrase();
        }else{
            throw new Exception("bad user");
        }

        return passphrase;
    }


    @Transactional
    public String generateNewPassPhrase()throws Exception {
        User currentUser = userFacade.getCurrentUser();
        String passphrase = null;
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);
            passphrase = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            logger.debug("new passphrase is : "+passphrase +" for media : "+media.getName());
            media.setPassPhrase(passphrase);
        }else{
            throw new Exception("bad user");
        }

        return passphrase;

    }

    @Transactional
    public String getExtId() throws Exception {
        String extId =null;

        User currentUser = userFacade.getCurrentUser();
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);
            extId  = media.getExtId();
        }else{
            throw new Exception("bad user");
        }

        return extId;
    }

    @Transactional
    public PriceInformationCommand getServices()throws Exception {

        PriceInformationCommand res = new PriceInformationCommand();
        User currentUser = userFacade.getCurrentUser();
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);
            List<CategoryPrice> minPriceByMediaType = media.getMinPriceByMediaType();
            Map<MediaType, List<CategoryPriceBean>> prices = new HashMap<MediaType, List<CategoryPriceBean>>();
            res.setPrices(prices);


            for (CategoryPrice categoryPrice : minPriceByMediaType) {
                MediaType mediaType = categoryPrice.getMediaType();
                List<CategoryPriceBean> categoryPriceBeans = prices.get(mediaType);
                if(categoryPriceBeans == null){
                    categoryPriceBeans = new ArrayList<CategoryPriceBean>();
                    prices.put(mediaType,categoryPriceBeans);
                }

                CategoryPriceBean categoryPriceBean = new CategoryPriceBean();
                categoryPriceBean.setMediaId(media.getId());
                categoryPriceBean.setCategory(categoryPrice.getCategory().getKey());
                categoryPriceBean.setMediaType(mediaType);
                categoryPriceBean.setUid(categoryPrice.getId().toString());
                categoryPriceBean.setId(categoryPrice.getId());
                categoryPriceBean.setPrice(categoryPrice.getMinPrice());

                categoryPriceBeans.add(categoryPriceBean);
            }



        }else{
            throw new Exception("bad user");
        }

        return res;
    }


    public void addService(PriceInformationCommand prices, CategoryPriceBean categoryPriceBean) {
        List<CategoryPriceBean> categoryPriceBeans = prices.getPrices().get(categoryPriceBean.getMediaType());
        if(categoryPriceBeans == null){
            categoryPriceBeans = new ArrayList<CategoryPriceBean>();
            prices.getPrices().put(categoryPriceBean.getMediaType(),categoryPriceBeans);
        }
        categoryPriceBean.setUid(UUID.randomUUID().toString());
        categoryPriceBeans.add(categoryPriceBean);
    }


    public void rmService(PriceInformationCommand prices, String uid) {

        Map<MediaType, List<CategoryPriceBean>> map = prices.getPrices();
        boolean found =false;
        for (Map.Entry<MediaType, List<CategoryPriceBean>> mediaTypeListEntry : map.entrySet()) {

            List<CategoryPriceBean> value = mediaTypeListEntry.getValue();
            CategoryPriceBean toDelete = null;
            for (CategoryPriceBean categoryPriceBean : value) {

                if(uid.equals(categoryPriceBean.getUid())){
                    found = true;
                    toDelete = categoryPriceBean;
                    break;
                }
            }
            if(found){
                value.remove(toDelete);
                break;
            }
        }
    }

    @Transactional
    public void saveServices(PriceInformationCommand prices) throws Exception {

        User currentUser = userFacade.getCurrentUser();
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);

            media.getMinPriceByMediaType().clear();

            List<CategoryPrice> minPriceByMediaType = media.getMinPriceByMediaType();

            for (List<CategoryPriceBean> categoryPriceBeans : prices.getPrices().values()) {
                for (CategoryPriceBean categoryPriceBean : categoryPriceBeans) {
                    CategoryPrice cat = null;
                    if(categoryPriceBean.getId()==null){
                        cat = new CategoryPrice();
                        cat.setCategory(categoryDao.findByKey(categoryPriceBean.getCategory()));
                        cat.setMediaType(categoryPriceBean.getMediaType());
                        cat.setMinPrice(categoryPriceBean.getPrice());
                    }else{
                        cat = categoryPriceDao.get(categoryPriceBean.getId());
                    }
                    minPriceByMediaType.add(cat);
                }
            }
            media.setMinPriceByMediaType(minPriceByMediaType);
        }




    }
}
