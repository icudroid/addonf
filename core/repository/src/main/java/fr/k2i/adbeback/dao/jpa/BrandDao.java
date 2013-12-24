package fr.k2i.adbeback.dao.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.ad.Brand;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("brandDao")
public class BrandDao extends GenericDaoJpa<Brand, Long> implements fr.k2i.adbeback.dao.IBrandDao,UserDetailsService {

    @Autowired
    private BrandRepository brandRepository;


    /**
     * Constructor that sets the entity to User.class.
     */
    public BrandDao() {
        super(Brand.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return brandRepository.findByEmail(username);
    }
}

