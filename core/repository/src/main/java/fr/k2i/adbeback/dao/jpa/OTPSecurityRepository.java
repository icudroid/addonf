package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.otp.OTPBrandSecurityConfirm;
import fr.k2i.adbeback.core.business.otp.OTPSecurity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 22/12/13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
@Repository("otpRepository")
public interface OTPSecurityRepository extends CrudRepository<OTPSecurity,Long>{
    OTPBrandSecurityConfirm findByBrand(Brand brand);
}
