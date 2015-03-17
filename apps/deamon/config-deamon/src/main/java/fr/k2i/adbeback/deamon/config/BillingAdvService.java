package fr.k2i.adbeback.deamon.config;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.company.billing.DayBilling;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.dao.IAdGameDao;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.IMonthBillingDao;
import fr.k2i.adbeback.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BillingAdvService {
    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private IMonthBillingDao monthBillingDao;

    private IBrandDao brandDao;

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
    @Scheduled(cron = "0 20 0 * * *")
    public void doAction() throws URISyntaxException, IOException {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        List<Brand> brands = brandDao.findAllHasActiveCampaign();

        for (Brand brand : brands) {
            Double sum = adGameDao.sumWinBidsForDate(brand, DateUtils.asDate(yesterday));
            MonthBilling monthBilling = monthBillingDao.findByMonth(brand,yesterday.getMonthValue(),yesterday.getYear());
            if(monthBilling == null){
                monthBilling = monthBillingDao.save(new MonthBilling(yesterday.getMonthValue(),yesterday.getYear(),brand));
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
