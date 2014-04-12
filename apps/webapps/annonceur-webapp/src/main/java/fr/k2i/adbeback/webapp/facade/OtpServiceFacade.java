package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.otp.OTPUserSecurityConfirm;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IOTPSecurityDao;
import fr.k2i.adbeback.dao.IWebUserDao;
import fr.k2i.adbeback.logger.LogHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: dimitri
 * Date: 10/04/14
 * Time: 14:42
 * Goal:
 */
@Component
public class OtpServiceFacade {
    private Logger logger = LogHelper.getLogger(this.getClass());
    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IOTPSecurityDao securityDao;

    @Resource(name = "annonceurUserDao")
    private IWebUserDao userDao;

    @Transactional
    public void removeOtp(String email, String name, String code) {
        User user = userDao.getUserByEmail(email);
        OTPUserSecurityConfirm otp = securityDao.findByUser(user);
        securityDao.remove(otp);
    }


    public enum ConfirmationRegistration {
        TIME_OUT,OK,KO;
    }

    @Transactional
    public ConfirmationRegistration confirmRegistration(String email, String name, String code) {

        User user = userDao.getUserByEmail(email);

        OTPUserSecurityConfirm otp = securityDao.findByUser(user);
        if(otp == null){
            return ConfirmationRegistration.KO;
        }else if(otp.getKey().equals(code)){
            Date now = new Date();
            if(otp.getExpirationDate().before(now)){
                return ConfirmationRegistration.TIME_OUT;
            }else{
                return ConfirmationRegistration.OK;
            }
        }else{
            return ConfirmationRegistration.KO;
        }
    }
}
