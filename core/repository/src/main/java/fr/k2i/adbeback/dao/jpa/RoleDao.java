package fr.k2i.adbeback.dao.jpa;

import java.util.List;

import fr.k2i.adbeback.core.business.player.Role_;
import fr.k2i.adbeback.dao.IRoleDao;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.player.Role;


/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> 
 */
@Repository
public class RoleDao extends GenericDaoJpa<Role, Long> implements IRoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDao() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    public Role getRoleByName(String rolename) {
        CriteriaBuilderHelper<Role> helper = new CriteriaBuilderHelper(getEntityManager(),Role.class);
        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(Role_.name), rolename)
        );

        List roles = helper.getResultList();
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }

    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getEntityManager().remove(role);
    }
}

