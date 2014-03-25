package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.QCity;
import fr.k2i.adbeback.core.business.user.Partner;
import fr.k2i.adbeback.core.business.user.QPartner;
import fr.k2i.adbeback.dao.ICityDao;
import fr.k2i.adbeback.dao.IPartnerDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PartnerDao extends GenericDaoJpa<Partner, Long> implements IPartnerDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public PartnerDao() {
        super(Partner.class);
    }


    @Override
    public Partner findbyExtId(String idPartnerExt) {
        QPartner qPartner = QPartner.partner;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qPartner).where(qPartner.extId.eq(idPartnerExt));

        return query.uniqueResult(qPartner);
    }
}

