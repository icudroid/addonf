package fr.k2i.adbeback.deamon.config;

import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.dao.IStatisticsDao;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
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

        LocalDate now = new LocalDate();
        LocalDate yesterday = now.minusDays(1);

        List<Statistics> stats = new ArrayList<Statistics>();
        stats.addAll(statisticsDao.doStatisticsViewedForDay(yesterday.toDate()));
        stats.addAll(statisticsDao.doStatisticsValidatedForDay(yesterday.toDate()));
        stats.addAll(statisticsDao.doStatisticsNoResponseForDay(yesterday.toDate()));
        stats.addAll(statisticsDao.doStatisticsNotValidatedForDay(yesterday.toDate()));

        for (Statistics stat : stats) {
            statisticsDao.save(stat);
        }
    }

}
