package fr.k2i.adbeback.service.impl;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.otp.OTPUserSecurityConfirm;
import fr.k2i.adbeback.core.business.player.WebUser;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.IOTPSecurityDao;
import fr.k2i.adbeback.dao.jpa.WebUserDao;
import fr.k2i.adbeback.service.BrandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service
public class BrandManagerImpl extends GenericManagerImpl<Brand, Long> implements BrandManager {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IBrandDao brandDao;

    @Autowired(required = false)
    private SaltSource saltSource;
    @Autowired
    private IOTPSecurityDao securityDao;

    @Autowired
    private WebUserDao webUserDao;

    @Autowired
    public void setPlayerDao(IBrandDao brandDao) {
        this.dao = brandDao;
        this.brandDao = brandDao;
    }


    @Transactional
    @Override
    public void changePasswd(User user, String newPwd) {
        if (saltSource == null) {
            // backwards compatibility
            user.setPassword(passwordEncoder.encodePassword(newPwd, null));
            log.warn("SaltSource not set, encrypting password w/o salt");
        } else {
            user.setPassword(passwordEncoder.encodePassword(newPwd, saltSource.getSalt(new WebUser(user))));
        }
    }



    @Transactional
    @Override
    public void changePasswd(String username, String newPwd) {
        User user = (User) webUserDao.loadUserByUsername(username);
        OTPUserSecurityConfirm otp = securityDao.findByUser(user);
        securityDao.remove(otp);
        if (saltSource == null) {
            // backwards compatibility
            user.setPassword(passwordEncoder.encodePassword(newPwd, null));
            log.warn("SaltSource not set, encrypting password w/o salt");
        } else {
            user.setPassword(passwordEncoder.encodePassword(newPwd, saltSource.getSalt(new WebUser(user))));
        }
    }

}
