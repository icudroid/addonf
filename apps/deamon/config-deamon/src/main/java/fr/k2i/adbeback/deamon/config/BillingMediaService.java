package fr.k2i.adbeback.deamon.config;

import fr.k2i.adbeback.core.business.company.billing.DayBilling;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.IAdGameDao;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.IMonthBillingDao;
import fr.k2i.adbeback.dao.IStatisticsDao;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
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
public class BillingMediaService {
    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private IMediaDao mediaDao;

    @Autowired
    private IMonthBillingDao monthBillingDao;

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
    @Scheduled(cron = "0 10 0 * * *")
    public void doAction() throws URISyntaxException, IOException {
        LocalDate now = new LocalDate();
        LocalDate yesterday = now.minusDays(1);

        List<Media> medias = mediaDao.getAll();

        for (Media media : medias) {
            Double sum = adGameDao.sumTransactionForDay(media,yesterday.toDate());
            MonthBilling monthBilling = monthBillingDao.findByMonth(media,yesterday.getMonthOfYear(),yesterday.getYear());
            if(monthBilling == null){
                monthBilling = monthBillingDao.save(new MonthBilling(yesterday.getMonthOfYear(),yesterday.getYear(),media));
            }

            DayBilling dayBilling = new DayBilling();
            dayBilling.setAmount(sum);
            dayBilling.setMonthBilling(monthBilling);

            BigDecimal sumMonth = new BigDecimal(""+monthBilling.getSum());
            sumMonth = sumMonth.add(new BigDecimal("" + sum));
            monthBilling.setSum(sumMonth.doubleValue());

        }

    }

}
