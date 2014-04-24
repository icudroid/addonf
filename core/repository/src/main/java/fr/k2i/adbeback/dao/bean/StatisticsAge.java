package fr.k2i.adbeback.dao.bean;

import fr.k2i.adbeback.core.business.player.AgeGroup;
import lombok.Data;

/**
 * User: dimitri
 * Date: 24/04/14
 * Time: 16:05
 * Goal:
 */
@Data
public class StatisticsAge{
    private AgeGroup ageGroup;
    private Long count;

    public StatisticsAge(AgeGroup ageGroup, Long count) {
        this.ageGroup = ageGroup;
        this.count = count;
    }
}
