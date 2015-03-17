package fr.k2i.adbeback.deamon.config;

import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
@Component
public class StatisticService {
    @Autowired
    private IStatisticsDao statisticsDao;

    /*
    Seconds 	  	    0-59 	  	, - * /
    Minutes 	  	    0-59 	  	, - * /
    Hours 	  	        0-23 	  	, - * /
    Day-of-month 	  	1-31 	  	, - * ? / L W
    Month 	  	        1-12 or JAN-DEC 	  	, - * /
    Day-of-Week 	  	1-7 or SUN-SAT 	  	, - * ? / L #
    Year (Optional) 	empty, 1970-2199 	  	, - * /
     */

    @Transactional
    @Scheduled(cron = "0 5 0 * * *")
    public void doAction() throws URISyntaxException, IOException {

        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        List<Statistics> stats = new ArrayList<Statistics>();
        stats.addAll(statisticsDao.doStatisticsViewedForDay(DateUtils.asDate(yesterday)));
        stats.addAll(statisticsDao.doStatisticsValidatedForDay(DateUtils.asDate(yesterday)));
        stats.addAll(statisticsDao.doStatisticsNoResponseForDay(DateUtils.asDate(yesterday)));
        stats.addAll(statisticsDao.doStatisticsNotValidatedForDay(DateUtils.asDate(yesterday)));

        for (Statistics stat : stats) {
            statisticsDao.save(stat);
        }
    }

}
