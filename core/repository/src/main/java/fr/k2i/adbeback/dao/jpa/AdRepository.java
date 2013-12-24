package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public interface AdRepository extends CrudRepository<Ad, Long> ,PagingAndSortingRepository<Ad,Long>{
    Page<Ad> findByBrand(Brand brand,Pageable pageable);
}
