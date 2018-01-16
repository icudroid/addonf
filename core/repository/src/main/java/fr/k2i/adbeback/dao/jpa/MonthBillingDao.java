package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.company.Company;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.core.business.company.billing.QMonthBilling;
import fr.k2i.adbeback.dao.IMonthBillingDao;
import org.springframework.stereotype.Repository;


@Repository
public class MonthBillingDao extends GenericDaoJpa<MonthBilling, Long> implements IMonthBillingDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public MonthBillingDao() {
        super(MonthBilling.class);
    }


    @Override
    public MonthBilling findByMonth(Company company, int month, int year) {
        QMonthBilling monthBilling = QMonthBilling.monthBilling;

        JPAQuery<MonthBilling> query = new JPAQuery(getEntityManager());

        query.from(monthBilling).where(
                monthBilling.company.eq(company),
                monthBilling.month.month.eq(month),
                monthBilling.month.year.eq(year)
        );

        return query.select(monthBilling).fetchOne();
    }
}

