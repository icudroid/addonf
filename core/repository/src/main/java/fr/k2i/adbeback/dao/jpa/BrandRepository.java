package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.ad.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 22/12/13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
@Repository("brandRepository")
public interface BrandRepository extends CrudRepository<Brand,Long> {
    Brand findByName(String name);

    Brand findByEmail(String email);

    Brand findBySiret(String siret);
}
