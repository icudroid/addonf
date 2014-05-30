package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.company.Company;
import fr.k2i.adbeback.core.business.user.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 13:55
 * Goal:
 */
public interface ICompanyDao extends IGenericDao<Company, Long> {
    @Transactional
    Company findByUser(User user);
}
