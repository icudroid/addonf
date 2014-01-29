package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.otp.*;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.IOTPSecurityDao;
import fr.k2i.adbeback.dao.IOneTimePasswordDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 14/01/14
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OTPSecurityDao extends GenericDaoJpa<OTPSecurity, Long> implements IOTPSecurityDao {
    public OTPSecurityDao() {
        super(OTPSecurity.class);
    }

    @Override
    public OTPBrandSecurityConfirm findByBrand(Brand brand) {
        QOTPBrandSecurityConfirm securityConfirm = QOTPBrandSecurityConfirm.oTPBrandSecurityConfirm;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(securityConfirm).where(securityConfirm.brand.eq(brand));

        return query.uniqueResult(securityConfirm);
    }
}
