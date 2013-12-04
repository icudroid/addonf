package fr.k2i.adbeback.dao.jpa;


import java.util.List;

import fr.k2i.adbeback.dao.ILookupDao;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.player.Role;

/**
 * Hibernate implementation of LookupDaoI.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Repository
public class LookupDao extends GenericDaoJpa<Role, Long> implements ILookupDao {


    public LookupDao() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");
        CriteriaBuilderHelper<Role> helper = new CriteriaBuilderHelper(getEntityManager(),Role.class);
        return helper.getResultList();
    }
}

