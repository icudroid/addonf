package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IBrandDao extends IGenericDao<Brand, Long> {


    List<Brand> getAllPossible(BrandRule rule);
}

