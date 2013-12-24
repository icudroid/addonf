package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Brand;
import org.springframework.data.repository.CrudRepository;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IBrandDao extends IGenericDao<Brand, Long> {

    
}

