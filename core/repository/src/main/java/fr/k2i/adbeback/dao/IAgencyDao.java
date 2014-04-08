package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.user.Agency;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IAgencyDao extends IGenericDao<Agency, Long> {

    @Transactional
    Agency findByName(String name);

    @Transactional
    Agency findByEmail(String email);

    @Transactional
    Agency findBySiret(String siret);
}

