package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.MediaUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 13:55
 * Goal:
 */
public interface IMediaDao extends IGenericDao<Media, Long> {
    @Transactional
    MediaUser findbyExtId(String idPartnerExt);

     boolean existTransaction(String idPartnerExt, String idTransactionExt);

    Media findByMediaUser(MediaUser user);
}
