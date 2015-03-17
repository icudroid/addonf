package fr.k2i.adbeback.core.business.player;


import fr.k2i.adbeback.date.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 24/01/14
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public enum AgeGroup{
    $0_9(0,9,"0-9"),
    $10_14(10,14,"10-14"),
    $15_17(15,17,"15-17"),
    $18_24(18,24,"18-24"),
    $25_34(25,34,"25-34"),
    $35_49(35,49,"35-49"),
    $50_64(50,64,"50-64"),
    _65_MORE(65,130,"65-+");

    private int min;
    private int max;
    private String label;

    private AgeGroup(int min, int max,String label) {
        this.min = min;
        this.max = max;
        this.label = label;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Date minDate() {
        LocalDateTime now = LocalDateTime.now();
        return DateUtils.asDate(now.minusYears(this.min));
    }

    public Date maxDate() {
        LocalDateTime now = LocalDateTime.now();
        return DateUtils.asDate(now.minusYears(this.max));
    }

    public static AgeGroup getGroupByBirhday(Date birthday) {
        Period period = Period.between(DateUtils.asLocalDate(birthday), LocalDate.now());
        int age = period.getYears();
        for (AgeGroup ageGroup : values()) {
            if(age >=ageGroup.getMin() && age <=ageGroup.getMax()){
                return ageGroup;
            }
        }
        return null;
    }

    public String label() {
        return label;
    }

    public static AgeGroup fromAge(Integer age) {
        for (AgeGroup ageGroup : values()) {
            if(age >=ageGroup.getMin() && age <=ageGroup.getMax()){
                return ageGroup;
            }
        }
        return null;
    }
}

