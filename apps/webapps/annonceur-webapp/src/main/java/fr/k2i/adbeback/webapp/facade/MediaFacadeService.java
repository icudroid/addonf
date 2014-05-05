package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.MediaUser;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IMediaDao;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 05/05/14
 * Time: 13:50
 * Goal:
 */
@Service
public class MediaFacadeService {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private UserFacade userFacade;

    @Autowired
    private IMediaDao mediaDao;

    @Transactional
    public String getMediaPassPhrase() throws Exception {
        String passphrase =null;

        User currentUser = userFacade.getCurrentUser();
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);
            passphrase = media.getPassPhrase();
        }else{
            throw new Exception("bad user");
        }

        return passphrase;
    }


    @Transactional
    public String generateNewPassPhrase()throws Exception {
        User currentUser = userFacade.getCurrentUser();
        String passphrase = null;
        if (currentUser instanceof MediaUser) {
            MediaUser user = (MediaUser) currentUser;
            Media media = mediaDao.findByMediaUser(user);
            passphrase = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            logger.debug("new passphrase is : "+passphrase +" for media : "+media.getName());
            media.setPassPhrase(passphrase);
        }else{
            throw new Exception("bad user");
        }

        return passphrase;

    }

}
