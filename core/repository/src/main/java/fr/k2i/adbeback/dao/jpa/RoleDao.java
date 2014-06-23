package fr.k2i.adbeback.dao.jpa;

import java.util.List;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.player.QRole;
import fr.k2i.adbeback.dao.IRoleDao;
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
        JPAQuery query = new JPAQuery(getEntityManager());
        QRole role = QRole.role;
        query.from(role).where(role.name.eq(rolename));

        List<Role> roles = query.list(role);
        if (roles.isEmpty()) {
            return null;
        } else {
            return roles.get(0);
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

