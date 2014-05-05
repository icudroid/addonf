package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.user.CategoryPrice;
import fr.k2i.adbeback.dao.ICategoryPriceDao;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryPriceDao  extends GenericDaoJpa<CategoryPrice, Long> implements ICategoryPriceDao {

    public CategoryPriceDao() {
        super(CategoryPrice.class);
    }

}
