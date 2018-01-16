package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.QAd;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.date.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("brandDao")
public class BrandDao extends GenericDaoJpa<Brand, Long> implements fr.k2i.adbeback.dao.IBrandDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public BrandDao() {
        super(Brand.class);
    }

    @Transactional
    @Override
    public List<Brand> getAllPossible(BrandRule rule) {
        List<Brand> noDisplayWith = rule.getNoDisplayWith();

        QBrand qBrand = QBrand.brand;
        JPAQuery query = new JPAQuery(getEntityManager());

        query.from(qBrand);
        if(!noDisplayWith.isEmpty()){
            query.where(qBrand.notIn(noDisplayWith));
        }

        return query.select(qBrand).fetch();

    }

    @Override
    public Brand findByName(String name) {
        QBrand qBrand = QBrand.brand;
        JPAQuery<Brand> query = new JPAQuery(getEntityManager());
        query.from(qBrand).where(qBrand.name.eq(name));
        return query.select(qBrand).fetchOne();
    }

/*    @Override
    public Brand findByEmail(String email) {
        QBrand qBrand = QBrand.brand;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qBrand).where(qBrand.email.eq(email));
        return query.uniqueResult(qBrand);
    }*/

    @Override
    public Brand findBySiret(String siret) {
        QBrand qBrand = QBrand.brand;
        JPAQuery<Brand> query = new JPAQuery(getEntityManager());
        query.from(qBrand).where(qBrand.siret.eq(siret));
        return query.select(qBrand).fetchOne();
    }

    @Override
    public List<Brand> findAllHasActiveCampaign() {
        QBrand qBrand = QBrand.brand;
        QAd ad = QAd.ad;

        LocalDate now = LocalDate.now();

        JPAQuery query = new JPAQuery(getEntityManager());
            query.from(ad).join(ad.brand,qBrand).where(
                    ad.startDate.loe(DateUtils.asDate(now)),
                    ad.endDate.goe(DateUtils.asDate(now))
            );
        return query.select(qBrand).fetch();
    }
}

