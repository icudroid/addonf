package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.otp.OTPBrandSecurityConfirm;
import fr.k2i.adbeback.core.business.otp.OTPSecurity;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 14:04
 * Goal:
 */
public interface IOTPSecurityDao extends IGenericDao<OTPSecurity, Long>{
    @Transactional
    OTPBrandSecurityConfirm findByBrand(Brand brand);
}
