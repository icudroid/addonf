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
public class StatisticsAgeSex {
    private AgeGroup ageGroup;
    private Sex sex;
    private Long count;

    public StatisticsAgeSex(AgeGroup ageGroup, Sex sex, Long count) {
        this.ageGroup = ageGroup;
        this.sex = sex;
        this.count = count;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(Age : ");

        if(getAgeGroup()==null){
            str.append("Inconnu");
        }else{
            str.append(getAgeGroup());
        }

        str.append(", nombre : ");
        str.append(getCount());
        str.append(")");

        return str.toString();
    }
}
