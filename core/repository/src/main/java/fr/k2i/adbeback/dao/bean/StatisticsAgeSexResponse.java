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
public class StatisticsAgeSexResponse extends StatisticsAgeSex{
    private String response;

    public StatisticsAgeSexResponse(AgeGroup ageGroup, Sex sex, Long count, String response) {
        super(ageGroup, sex, count);
        this.response = response;
    }
}
