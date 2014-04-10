package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.user.Agency;
import fr.k2i.adbeback.core.business.user.AgencyUser;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IAgencyDao;
import fr.k2i.adbeback.dao.IRoleDao;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyRole;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * User: dimitri
 * Date: 10/04/14
 * Time: 15:24
 * Goal:
 */
@Component
public class AgencyServiceFacade {

    private Logger logger = LogHelper.getLogger(this.getClass());

    @Autowired
    private IAgencyDao agencyDao;


    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IMailEngine mailEngine;

    @Value("${agency.base.admin.confirm.url}")
    private String agencyAdminConfirmBaseUrl;

    @Value("${agency.base.user.confirm.url}")
    private String agencyUserConfirmBaseUrl;


    @Autowired
    private MessageSource messageSource;


    @Autowired
    private IRoleDao roleDao;

    @Transactional
    public void sendAgencyUserValidation(Long agencyId, Locale locale) {
        Agency agency = agencyDao.get(agencyId);
        List<AgencyUser> users = agency.getUsers();
        for (AgencyUser user : users) {
            if(!user.getRoles().contains(roleDao.getRoleByName(AgencyRole.ADMIN.name()))){
                //create users and send validate account when le administrator has validate her account
                String url = agencyUserConfirmBaseUrl + desCryptoService.generateOtpConfirm(agency.getName() + "|" + user.getEmail(), user, 48);

                Map<String, Object> model = new HashMap<String, Object>();

                model.put("url", url);
                model.put("user", user);
                model.put("agency", agency);

                Email email = Email.builder()
                        .subject(messageSource.getMessage("mail.enrolled.agency.user", new Object[]{}, locale))
                        .model(model)
                        .content("email/agency_user_enrolled")
                        .recipients(user.getEmail())
                        .noAttachements()
                        .build();
                try {
                    mailEngine.sendMessage(email, locale);
                } catch (SendException e) {
                    logger.error("error sending email", e);
                }
            }
        }
    }
}
