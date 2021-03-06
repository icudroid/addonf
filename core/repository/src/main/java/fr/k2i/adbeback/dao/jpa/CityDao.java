package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.QCity;
import fr.k2i.adbeback.dao.IAdRuleDao;
import fr.k2i.adbeback.dao.ICityDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CityDao extends GenericDaoJpa<City, Long> implements ICityDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public CityDao() {
        super(City.class);
    }


    @Override
    public List<City> findByZipcodeAndCountry_Code(String zipcode, String code) {
        QCity city = QCity.city1;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(city).where(city.zipcode.eq(zipcode).and(city.country.code.eq(code)));
        return query.select(city).fetch();
    }


    @Override
    public List<City> findByZipcode(String zipcode) {
        QCity city = QCity.city1;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(city).where(city.zipcode.eq(zipcode));
        return query.select(city).fetch();
    }

    @Override
    public City findByZipcodeAndCityAndCountry_Code(String zipcode, String city, String code) {
        QCity qcity = QCity.city1;

        JPAQuery<City> query = new JPAQuery(getEntityManager());
        query.from(qcity).where(qcity.zipcode.eq(zipcode).and(qcity.country.code.eq(code)));
        return query.select(qcity).fetchOne();

    }

    @Override
    public List<City> findByCityStartingWith(String name) {
        QCity city = QCity.city1;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(city).where(city.city.startsWithIgnoreCase(name));
        return query.select(city).fetch();

    }
}

