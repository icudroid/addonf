package fr.k2i.adbeback.dao.jpa;

import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.user.Agency;
import fr.k2i.adbeback.core.business.user.QAgency;
import fr.k2i.adbeback.core.business.user.QAgencyUser;
import fr.k2i.adbeback.dao.IAgencyDao;
import org.springframework.stereotype.Repository;

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
@Repository
public class AgencyDao extends GenericDaoJpa<Agency, Long> implements IAgencyDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AgencyDao() {
        super(Agency.class);
    }


    @Override
    public Agency findByName(String name) {
        QAgency agency = QAgency.agency;
        JPAQuery<Agency> query = new JPAQuery(getEntityManager());
        query.from(agency).where(agency.name.eq(name));
        return query.select(agency).fetchOne();
    }

    @Override
    public Agency findBySiret(String siret) {
        QAgency agency = QAgency.agency;
        JPAQuery<Agency> query = new JPAQuery(getEntityManager());
        query.from(agency).where(agency.siret.eq(siret));
        return query.select(agency).fetchOne();
    }

    @Override
    public List<Brand> findAllBrandsForAgency(Agency agency) {
        QAgency qagency = QAgency.agency;
        JPAQuery query = new JPAQuery(getEntityManager());
        QAgencyUser user = QAgencyUser.agencyUser;
        QBrand brand = QBrand.brand;
        query.from(qagency).join(qagency.users,user).join(user.inChargeOf,brand).where(qagency.eq(agency));
        query.distinct();
        return query.select(brand).fetch();
    }
}

