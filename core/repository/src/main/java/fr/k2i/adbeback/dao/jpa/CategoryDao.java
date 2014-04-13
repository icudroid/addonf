package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.QAdGameTransaction;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.dao.ICategoryDao;
import fr.k2i.adbeback.dao.IMediaDao;
import org.springframework.stereotype.Repository;


@Repository
public class CategoryDao extends GenericDaoJpa<Category, Long> implements ICategoryDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public CategoryDao() {
        super(Category.class);
    }

    @Override
    public Category findByKey(String key) {

        QCategory category = QCategory.category;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(category).where(category.key.eq(key));

        return query.uniqueResult(category);
    }
}

