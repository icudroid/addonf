package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.dao.IAdRuleDao;
import org.springframework.stereotype.Repository;


@Repository
public class AdRuleDao extends GenericDaoJpa<AdRule, Long> implements IAdRuleDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdRuleDao() {
        super(AdRule.class);
    }


}

