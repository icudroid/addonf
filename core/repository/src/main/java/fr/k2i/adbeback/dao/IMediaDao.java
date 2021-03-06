package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.user.CategoryPrice;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.MediaType;
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
    Media findByExtId(String idPartnerExt);

    @Transactional
    boolean existTransaction(String idPartnerExt, String idTransactionExt);

    @Transactional
    Media findByMediaUser(MediaUser user);

    @Transactional
    CategoryPrice findCategoryPrice(Long idMedia, MediaType mediaType, String categoryKey);
}
