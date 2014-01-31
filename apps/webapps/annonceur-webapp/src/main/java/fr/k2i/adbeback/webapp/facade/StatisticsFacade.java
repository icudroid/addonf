package fr.k2i.adbeback.webapp.facade;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.dao.IAdDao;
import fr.k2i.adbeback.dao.IAdRuleDao;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;
import fr.k2i.adbeback.webapp.bean.LabelData;
import fr.k2i.adbeback.webapp.bean.StatistBeanSearch;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private IBrandDao brandDao;

    @Autowired
    private IAdRuleDao adRuleDao;

    @Autowired
    private IAdDao adDao;

    @Transactional
    public void global(Long idAd, Map<String, Object> model) throws Exception {


        Brand currentUser = userFacade.getCurrentUser();

        Ad ad = adDao.get(idAd);
        if(!ad.getBrand().equals(currentUser)){
            throw new Exception("bad user");
        }

        long global = statisticsDao.nbGlobal(ad);
        long validated = statisticsDao.nbValidated(ad);

        model.put("global",global);
        model.put("validated",validated);
    }






    public List<LabelData> doSearch(StatistBeanSearch search) {
        Ad ad = adDao.get(search.getIdAd());
        AdRule adRule = null;

        if(search.getServiceId()!=null){
            adRule = adRuleDao.get(search.getServiceId());
        }

        switch (search.getType()){
            case AGE_GOUPE:{
                List<StatisticsDao.StatisticsAge> statisticsAges = null;

                if(search.isGlobal()){
                    statisticsAges = statisticsDao.nbGlobalGroupByGroupAge(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsAges = statisticsDao.nbValidatedGroupByGroupAge(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsDao.StatisticsAge statisticsAge : statisticsAges) {
                    res.add(new LabelData(statisticsAge.getAgeGroup().label(),statisticsAge.getCount()));
                }
                return res;
            }
            case AGE_GROUP_SEX:{

                List<StatisticsDao.StatisticsAgeSex> statisticsAgeSexes = null;

                if(search.isGlobal()){
                    statisticsAgeSexes = statisticsDao.nbGlobalGroupByGroupAgeAndSex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsAgeSexes = statisticsDao.nbValidatedGroupByGroupAgeAndSex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsDao.StatisticsAgeSex statisticsAgeSex : statisticsAgeSexes) {
                    res.add(new LabelData(statisticsAgeSex.getSex()+" "+statisticsAgeSex.getAgeGroup().label(),statisticsAgeSex.getCount()));
                }
                return res;
            }
            case CITY:{
                List<StatisticsDao.StatisticsCity> statisticsCities= null;

                if(search.isGlobal()){
                    statisticsCities = statisticsDao.nbGlobalGroupByCity(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsCities = statisticsDao.nbValidatedGroupByCity(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsDao.StatisticsCity statisticsCity : statisticsCities) {
                    res.add(new LabelData(statisticsCity.getCity().getCity(), statisticsCity.getCount()));
                }
                return res;
            }
            case SEX:{
                List<StatisticsDao.StatisticsSex> statisticsSexes= null;

                if(search.isGlobal()){
                    statisticsSexes = statisticsDao.nbGlobalGroupBySex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsSexes = statisticsDao.nbValidatedGroupBySex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsDao.StatisticsSex statisticsCity : statisticsSexes) {
                    res.add(new LabelData(statisticsCity.getSex().toString(), statisticsCity.getCount()));
                }
                return res;
            }
            default:
                return new ArrayList<LabelData>();
        }
    }
}
