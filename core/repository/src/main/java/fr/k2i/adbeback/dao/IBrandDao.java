package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IBrandDao extends IGenericDao<Brand, Long> {
    @Transactional
    List<Brand> getAllPossible(BrandRule rule);

    @Transactional
    Brand findByName(String name);

/*    @Transactional
    Brand findByEmail(String email);*/

    @Transactional
    Brand findBySiret(String siret);

    @Transactional
    List<Brand> findAllHasActiveCampaign();

}

