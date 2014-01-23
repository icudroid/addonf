package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IStatisticsDao extends IGenericDao<Ad, Long> {


    long nbGlobal(Ad ad, Date start, Date end);

    List<StatisticsDao.Statistics> nbGlobalGroupByService(Ad ad, Date start, Date end, List<Class<? extends AdService>> services);

    List<StatisticsDao.StatisticsAge> nbGlobalGroupAge(Ad ad, Date start, Date end);

    StatisticsDao.StatisticsAge nbGlobalGroupAge(Ad ad, Date start, Date end, StatisticsDao.AgeGroup ageGroup);
}

