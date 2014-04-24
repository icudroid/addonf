package fr.k2i.adbeback.dao.bean;

import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

/**
 * User: dimitri
 * Date: 24/04/14
 * Time: 16:06
 * Goal:
 */
@Data
public class StatisticsSex{
    private Sex sex;
    private Long count;

    public StatisticsSex(Sex sex, Long count) {
        this.sex = sex;
        this.count = count;
    }
}
