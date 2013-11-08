package fr.k2i.adbeback.dao.hibernate;


import java.util.List;

import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.dao.LookupDao;

/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Repository
public class LookupDaoHibernate extends GenericDaoHibernate<Role, Long>implements LookupDao {


    public LookupDaoHibernate() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return getHibernateTemplate().find("from Role order by name");
    }
}

