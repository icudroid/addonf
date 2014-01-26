package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.QAd;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.QBrandRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.ad.Brand;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private BrandRepository brandRepository;


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

        return query.list(qBrand);

    }
}

