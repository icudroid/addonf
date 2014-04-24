package fr.k2i.adbeback.dao.bean;

import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import java.util.List;

/**
 * User: dimitri
 * Date: 24/04/14
 * Time: 16:06
 * Goal:
 */
@Data
public class StatisticsAgeSexMulti extends StatisticsAgeSex{
    private List<Boolean> responses;
    public StatisticsAgeSexMulti(AgeGroup ageGroup, Sex sex, Long count, List<Boolean> responses) {
        super(ageGroup, sex, count);
        this.responses = responses;
    }
}
