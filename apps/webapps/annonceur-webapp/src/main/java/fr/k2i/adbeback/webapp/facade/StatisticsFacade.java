package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;
import fr.k2i.adbeback.webapp.bean.LabelData;
import fr.k2i.adbeback.webapp.bean.StatisticSearchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private IAdGameDao adGameDao;

    @Value("${addonf.logo.location}")
    private String logoPath;


    @Transactional
    public void global(Long idAd, Map<String, Object> model) throws Exception {


        Brand currentUser = userFacade.getCurrentUser();

        Ad ad = adDao.get(idAd);
        if(!ad.getBrand().equals(currentUser)){
            throw new Exception("bad user");
        }

        long global = statisticsDao.nbGlobal(ad,null,null,null);
        long validated = statisticsDao.nbValidated(ad,null,null,null);

        model.put("global",global);
        model.put("validated",validated);
    }






    public List<LabelData> doSearch(StatisticSearchBean search) {
        Ad ad = adDao.get(search.getIdAd());
        AdRule adRule = null;

        if(search.getServiceId()!=null){
            adRule = adRuleDao.get(search.getServiceId());
        }

        switch (search.getType()){
            case AGE_GROUPE:{
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

    public void detail(Long idAd, Map<String, Object> model) throws Exception {
        Brand currentUser = userFacade.getCurrentUser();

        Ad ad = adDao.get(idAd);
        if(!ad.getBrand().equals(currentUser)){
            throw new Exception("bad user");
        }

        List<AdService> rules = ad.getRules(AdService.class);

        fr.k2i.adbeback.webapp.bean.AdService service = new fr.k2i.adbeback.webapp.bean.AdService();

        for (AdService adRule : rules) {
            boolean used = adGameDao.RuleIsUsed(adRule);
            service.addService(adRule,logoPath,used);
        }

        model.put("services",service);

        model.put("minDate",ad.getStartDate());
        model.put("maxDate",ad.getEndDate());


    }
}
