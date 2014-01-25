package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.core.business.statistic.StatisticsValidated;
import fr.k2i.adbeback.core.business.statistic.StatisticsViewed;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;

import java.util.Date;
import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IStatisticsDao extends IGenericDao<Statistics, Long> {


    long nbGlobal(Ad ad, Date start, Date end);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupByGroupAge(Ad ad, Date start, Date end);

    StatisticsDao.StatisticsAge nbGlobalGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup);

    List<StatisticsDao.StatisticsCity> nbGlobalGroupByCity(Ad ad, Date start, Date end);





    long nbValidated(Ad ad, Date start, Date end);

    List<StatisticsDao.Statistics> nbValidatedGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service);

    List<StatisticsDao.StatisticsAge> nbValidatedGroupByGroupAge(Ad ad, Date start, Date end);

    StatisticsDao.StatisticsAge nbValidatedGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup);

    List<StatisticsDao.StatisticsCity> nbValidatedGroupByCity(Ad ad, Date start, Date end);





    long nbNotValidated(Ad ad, Date start, Date end);

    List<StatisticsDao.Statistics> nbNotValidatedGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service);

    StatisticsDao.StatisticsAge nbNotValidatedGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup);

    List<StatisticsDao.StatisticsAge> nbNotValidatedGroupByGroupAge(Ad ad, Date start, Date end);

    List<StatisticsDao.StatisticsCity> nbNotValidatedGroupByCity(Ad ad, Date start, Date end);


    long nbGlobal(Ad ad);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad, Class<? extends AdService> service);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad);

    StatisticsDao.StatisticsAge nbGlobalGroupByGroupAge(Ad ad, AgeGroup ageGroup);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupByGroupAge(Ad ad);

    List<StatisticsDao.StatisticsCity> nbGlobalGroupByCity(Ad ad);

    long nbValidated(Ad ad);

    List<StatisticsDao.Statistics> nbValidatedGroupByService(Ad ad);

    List<StatisticsDao.Statistics> nbValidatedGroupByService(Ad ad, Class<? extends AdService> service);

    StatisticsDao.StatisticsAge nbValidatedGroupByGroupAge(Ad ad, AgeGroup ageGroup);

    List<StatisticsDao.StatisticsAge> nbValidatedGroupByGroupAge(Ad ad);

    List<StatisticsDao.StatisticsCity> nbValidatedGroupByCity(Ad ad);

    long nbNotValidated(Ad ad);

    List<StatisticsDao.Statistics> nbNotValidatedGroupByService(Ad ad);

    List<StatisticsDao.Statistics> nbNotValidatedGroupByService(Ad ad, Class<? extends AdService> service);

    StatisticsDao.StatisticsAge nbNotValidatedGroupByGroupAge(Ad ad, AgeGroup ageGroup);

    List<StatisticsDao.StatisticsAge> nbNotValidatedGroupByGroupAge(Ad ad);

    List<StatisticsDao.StatisticsCity> nbNotValidatedGroupByCity(Ad ad);


    List<StatisticsValidated> doStatisticsValidatedForDay(Date day);

    List<StatisticsViewed> doStatisticsViewedForDay(Date day);
}

