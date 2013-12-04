package fr.k2i.adbeback.dao;


import java.util.List;

import fr.k2i.adbeback.core.business.player.Role;

/**
 * Lookup Data Access Object (IGenericDao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface ILookupDao {
    //~ Methods ================================================================

    /**
     * Returns all Roles ordered by name
     * @return populated list of roles
     */
    List<Role> getRoles();
}

