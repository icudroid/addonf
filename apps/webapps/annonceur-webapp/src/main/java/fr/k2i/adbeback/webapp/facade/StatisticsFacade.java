package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.user.AgencyUser;
import fr.k2i.adbeback.core.business.user.BrandUser;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.dao.bean.*;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;
import fr.k2i.adbeback.webapp.bean.LabelData;
import fr.k2i.adbeback.webapp.bean.StatisticSearchBean;
import fr.k2i.adbeback.webapp.bean.statistic.RuleBean;
import fr.k2i.adbeback.webapp.controller.StatisticsController;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
    private IStatisticsRealTimeDao statisticsRealTimeDao;

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


    private Brand getBrandForConnectedUser() throws Exception {
        Brand brand = null;

        User user = userFacade.getCurrentUser();
        if (user instanceof BrandUser) {
            BrandUser brandUser = (BrandUser) user;
            brand = brandUser.getBrand();
        }
        return brand;
    }

    @Transactional
    public void global(Long idAd, Map<String, Object> model) throws Exception {


        //Brand brand = getBrandForConnectedUser();

        User currentUser = userFacade.getCurrentUser();

        Ad ad = adDao.get(idAd);

        if (currentUser instanceof BrandUser) {
            BrandUser user = (BrandUser) currentUser;
            Brand brand = user.getBrand();

            if(!ad.getBrand().equals(brand)){
                throw new Exception("bad user");
            }

        }else if (currentUser instanceof AgencyUser) {
            AgencyUser user = (AgencyUser) currentUser;
            List<Brand> inChargeOf = user.getInChargeOf();
            if(!inChargeOf.contains(ad.getBrand())){
                throw new Exception("bad user");
            }
        }


        long global = statisticsRealTimeDao.computeNbViewGlobal(ad);
        long validated = statisticsRealTimeDao.computeNbValidated(ad);

        List<RuleBean> rules = new ArrayList<RuleBean>();
        for (AdService rule : ad.getRules(AdService.class)) {
            rules.add(new RuleBean(rule));
/*            statisticsRealTimeDao.computeResponsesPlayerOk(rule);
            statisticsRealTimeDao.computeResponsesPlayerKo(rule);
            statisticsRealTimeDao.computeNbView(rule);
            statisticsRealTimeDao.computeNoResponses(rule);

            if (rule instanceof OpenRule) {
                statisticsRealTimeDao.computeResponsesPlayer((OpenRule) rule);
            }

            if (rule instanceof MultiResponseRule) {
                statisticsRealTimeDao.computeResponsesPlayer((MultiResponseRule) rule);
            }

            if (rule instanceof BrandRule) {
                statisticsRealTimeDao.computeResponsesPlayer((BrandRule) rule);
            }*/

        }

        model.put("rules",rules);
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
                List<StatisticsAge> statisticsAges = null;

                if(search.isGlobal()){
                    statisticsAges = statisticsDao.nbGlobalGroupByGroupAge(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsAges = statisticsDao.nbValidatedGroupByGroupAge(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsAge statisticsAge : statisticsAges) {
                    res.add(new LabelData(statisticsAge.getAgeGroup().label(),statisticsAge.getCount()));
                }
                return res;
            }
            case AGE_GROUP_SEX:{

                List<StatisticsAgeSex> statisticsAgeSexes = null;

                if(search.isGlobal()){
                    statisticsAgeSexes = statisticsDao.nbGlobalGroupByGroupAgeAndSex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsAgeSexes = statisticsDao.nbValidatedGroupByGroupAgeAndSex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsAgeSex statisticsAgeSex : statisticsAgeSexes) {
                    res.add(new LabelData(statisticsAgeSex.getSex()+" "+statisticsAgeSex.getAgeGroup().label(),statisticsAgeSex.getCount()));
                }
                return res;
            }
            case CITY:{
                List<StatisticsCity> statisticsCities= null;

                if(search.isGlobal()){
                    statisticsCities = statisticsDao.nbGlobalGroupByCity(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsCities = statisticsDao.nbValidatedGroupByCity(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsCity statisticsCity : statisticsCities) {
                    res.add(new LabelData(statisticsCity.getCity().getCity(), statisticsCity.getCount()));
                }
                return res;
            }
            case SEX:{
                List<StatisticsSex> statisticsSexes= null;

                if(search.isGlobal()){
                    statisticsSexes = statisticsDao.nbGlobalGroupBySex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }else{
                    statisticsSexes = statisticsDao.nbValidatedGroupBySex(ad, search.getStart(), search.getEnd(),(AdService)adRule);
                }

                List<LabelData> res = new ArrayList<LabelData>();
                for (StatisticsSex statisticsCity : statisticsSexes) {
                    res.add(new LabelData(statisticsCity.getSex().toString(), statisticsCity.getCount()));
                }
                return res;
            }
            default:
                return new ArrayList<LabelData>();
        }
    }

    public void detail(Long idAd, Map<String, Object> model) throws Exception {
        Brand brand = getBrandForConnectedUser();

        Ad ad = adDao.get(idAd);
        if(!ad.getBrand().equals(brand)){
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

    @Data
    public class RealTimeResponse implements Serializable{

        private String question;
        private Long count;
        private List<LabelData> ok;
        private List<LabelData> ko;
        private List<LabelData> noResponse;

        private List<StatisticsAgeSexResponse> open;
        private List<StatisticsAgeSexBrand> brand;
        private List<StatisticsAgeSexMulti> multi;

    }

    @Transactional
    public RealTimeResponse realTimeStat(Long idService) {
        RealTimeResponse res = new RealTimeResponse();

        AdService rule = (AdService) adRuleDao.get(idService);

        res.setQuestion(rule.getQuestion());

        if (rule instanceof OpenRule) {
            res.setOpen(statisticsRealTimeDao.computeResponsesPlayer((OpenRule) rule));
        }

        if (rule instanceof MultiResponseRule) {
            //Todo :
            statisticsRealTimeDao.computeResponsesPlayer((MultiResponseRule)rule);
        }

        if (rule instanceof BrandRule) {
            res.setBrand(statisticsRealTimeDao.computeResponsesPlayer((BrandRule) rule));
        }

        List<StatisticsAgeSex> sKo = statisticsRealTimeDao.computeResponsesPlayerKo(rule);
        List<LabelData> rko = new ArrayList<LabelData>();
        for (StatisticsAgeSex statisticsAgeSex : sKo) {
            rko.add(new LabelData(statisticsAgeSex.toString(), statisticsAgeSex.getCount()));
        }
        res.setKo(rko);

        List<StatisticsAgeSex> sok = statisticsRealTimeDao.computeResponsesPlayerOk(rule);
        List<LabelData> rok = new ArrayList<LabelData>();
        for (StatisticsAgeSex statisticsAgeSex : sok) {
            rok.add(new LabelData(statisticsAgeSex.toString(), statisticsAgeSex.getCount()));
        }
        res.setOk(rok);

        List<StatisticsAgeSex> sNoResp = statisticsRealTimeDao.computeNoResponses(rule);
        List<LabelData> rNoResp = new ArrayList<LabelData>();
        for (StatisticsAgeSex statisticsAgeSex : sNoResp) {
            rNoResp.add(new LabelData(statisticsAgeSex.toString(), statisticsAgeSex.getCount()));
        }
        res.setNoResponse(rNoResp);

        res.setCount(statisticsRealTimeDao.computeNbView(rule));

        return res;
    }
}
