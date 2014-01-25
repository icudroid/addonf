package fr.k2i.adbeback.core.business.statistic;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 14/01/14
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
@Data
@Entity
@DiscriminatorValue("Viewed")
public class StatisticsViewed extends Statistics{

    public StatisticsViewed(){
        super();
    }

    public StatisticsViewed(Date day, Sex sex, City city, AdService service, AgeGroup ageGroup, Long nb) {
        super(day, sex, city, service, ageGroup, nb);
    }
}
