package fr.k2i.adbeback.webapp.facade;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.dao.IAdDao;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.jpa.BrandRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: dimitri
 * Date: 24/01/14
 * Time: 10:49
 * Goal:
 */
@Component
public class StatisticsFacade {


    @Autowired
    private UserFacade userFacade;

    @Autowired
    private IStatisticsDao statisticsDao;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private IAdDao adDao;

    @Transactional
    public void global(Long idAd, Map<String, Object> model) throws Exception {

        Brand currentUser = userFacade.getCurrentUser();

        Ad ad = adDao.get(idAd);
        if(!ad.getBrand().equals(currentUser)){
            throw new Exception("bad user");
        }

        model.put("global",statisticsDao.nbGlobal(ad));
        model.put("validated",statisticsDao.nbValidated(ad));
        model.put("notValidated",statisticsDao.nbNotValidated(ad));

    }
}
