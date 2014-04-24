package fr.k2i.adbeback.dao.bean;

import fr.k2i.adbeback.core.business.country.City;
import lombok.Data;

/**
 * User: dimitri
 * Date: 24/04/14
 * Time: 16:06
 * Goal:
 */
@Data
public class StatisticsCity{
    private City city;
    private Long count;

    public StatisticsCity(City city, Long count) {
        this.city = city;
        this.count = count;
    }
}
