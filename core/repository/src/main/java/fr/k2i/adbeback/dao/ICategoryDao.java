package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.user.Category;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.MediaUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: dimitri
 * Date: 29/01/14
 * Time: 13:55
 * Goal:
 */
public interface ICategoryDao extends IGenericDao<Category, Long> {

    Category findByKey(String category);
}
