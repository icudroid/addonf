package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.user.Media;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 13:55
 * Goal:
 */
public interface IMediaDao extends IGenericDao<Media, Long> {
    @Transactional
    Media findbyExtId(String idPartnerExt);

     boolean existTransaction(String idPartnerExt, String idTransactionExt);
}
