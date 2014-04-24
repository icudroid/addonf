package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.core.business.statistic.StatisticsValidated;
import fr.k2i.adbeback.core.business.statistic.StatisticsViewed;
import fr.k2i.adbeback.dao.bean.StatisticsAge;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSex;
import fr.k2i.adbeback.dao.bean.StatisticsCity;
import fr.k2i.adbeback.dao.bean.StatisticsSex;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;

import java.util.Date;
import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IStatisticsDao extends IGenericDao<Statistics, Long> {
    List<StatisticsViewed> doStatisticsViewedForDay(Date day);
    List<StatisticsValidated> doStatisticsValidatedForDay(Date day);
    void removeAllInDay(Date day);

    List<StatisticsAge> nbGlobalGroupByGroupAge(Ad ad, Date start, Date end, AdService service);
    List<StatisticsAge> nbValidatedGroupByGroupAge(Ad ad, Date start, Date end, AdService service);

    List<StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AdService service);
    List<StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AdService service);

    List<StatisticsCity> nbGlobalGroupByCity(Ad ad, Date start, Date end, AdService service);
    List<StatisticsCity> nbValidatedGroupByCity(Ad ad, Date start, Date end, AdService service);

    List<StatisticsSex> nbGlobalGroupBySex(Ad ad, Date start, Date end, AdService service);
    List<StatisticsSex> nbValidatedGroupBySex(Ad ad, Date start, Date end, AdService service);

    long nbGlobal(Ad ad, Date start, Date end,AdService service);
    long nbValidated(Ad ad, Date start, Date end,AdService service);



/*    List<StatisticsViewed> doStatisticsViewedForDay(Date day);

    List<StatisticsValidated> doStatisticsValidatedForDay(Date day);

    long nbGlobal(Ad ad);

    long nbGlobal(Ad ad, Date start, Date end);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad, Class<? extends AdService> service);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service);

    StatisticsDao.Statistics nbGlobalForService(Ad ad, AdService service);

    StatisticsDao.Statistics nbGlobalForService(Ad ad, Date start, Date end, AdService service);

    StatisticsDao.StatisticsAge nbGlobalGroupByGroupAge(Ad ad, AgeGroup ageGroup);

    StatisticsDao.StatisticsAge nbGlobalGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup);

    StatisticsDao.StatisticsAgeSex nbGlobalGroupByGroupAgeAndSex(Ad ad, AgeGroup ageGroup, Sex sex);

    StatisticsDao.StatisticsAgeSex nbGlobalGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AgeGroup ageGroup, Sex sex);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupByGroupAge(Ad ad);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupByGroupAge(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSex(Ad ad);

    List<StatisticsDao.StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSex(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsCity> nbGlobalGroupByCity(Ad ad);

    List<StatisticsDao.StatisticsCity> nbGlobalGroupByCity(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupByGroupAgeForService(Ad ad, AdService service);

    long nbValidated(Ad ad);

    long nbValidated(Ad ad, Date start, Date end);

    List<StatisticsDao.Statistics> nbValidatedGroupByService(Ad ad);

    List<StatisticsDao.Statistics> nbValidatedGroupByService(Ad ad, Class<? extends AdService> service);

    List<StatisticsDao.Statistics> nbValidatedGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service);

    StatisticsDao.Statistics nbValidatedForService(Ad ad, AdService service);

    StatisticsDao.Statistics nbValidatedForService(Ad ad, Date start, Date end, AdService service);

    StatisticsDao.StatisticsAge nbValidatedGroupByGroupAge(Ad ad, AgeGroup ageGroup);

    StatisticsDao.StatisticsAge nbValidatedGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup);

    StatisticsDao.StatisticsAgeSex nbValidatedGroupByGroupAgeAndSex(Ad ad, AgeGroup ageGroup, Sex sex);

    StatisticsDao.StatisticsAgeSex nbValidatedGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AgeGroup ageGroup, Sex sex);

    List<StatisticsDao.StatisticsAge> nbValidatedGroupByGroupAge(Ad ad);

    List<StatisticsDao.StatisticsAge> nbValidatedGroupByGroupAge(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSex(Ad ad);

    List<StatisticsDao.StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSex(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsCity> nbValidatedGroupByCity(Ad ad);

    List<StatisticsDao.StatisticsCity> nbValidatedGroupByCity(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSexForService(Ad ad, AdService service);

    List<StatisticsDao.StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSexForService(Ad ad, AdService service);

    List<StatisticsDao.StatisticsAge> nbValidatedGroupByGroupAgeForService(Ad ad, AdService service);

    void removeAllInDay(Date day);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupByGroupAgeForService(Ad ad, Date start, Date end, AdService service);

    List<StatisticsDao.StatisticsAge> nbValidatedGroupByGroupAgeForService(Ad ad, Date start, Date end, AdService service);*/


}

