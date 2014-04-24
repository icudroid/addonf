package fr.k2i.adbeback.dao.bean;

import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

/**
 * User: dimitri
 * Date: 24/04/14
 * Time: 16:06
 * Goal:
 */
@Data
public class StatisticsAgeSexBrand extends StatisticsAgeSex{
    private String logo;

    public StatisticsAgeSexBrand(AgeGroup ageGroup, Sex sex, Long count, String logo) {
        super(ageGroup, sex, count);
        this.logo = logo;
    }
}
