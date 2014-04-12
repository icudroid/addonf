package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Sector;
import fr.k2i.adbeback.core.business.user.MediaUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 13:55
 * Goal:
 */
public interface ISectorDao extends IGenericDao<Sector, Long> {
    @Transactional
    Sector findbyCode(String code);


}
