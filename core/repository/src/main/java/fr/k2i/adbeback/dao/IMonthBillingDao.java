package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.company.Company;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.core.business.user.CategoryPrice;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.core.business.user.MediaUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 13:55
 * Goal:
 */
public interface IMonthBillingDao extends IGenericDao<MonthBilling, Long> {

    @Transactional
    MonthBilling findByMonth(Company company, int month, int year);
}
