package fr.k2i.adbeback.core.business.player;

import org.joda.time.DateTime;
import org.joda.time.Years;

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
        DateTime now = new DateTime();
        return now.minusYears(this.min).toDate();
    }

    public Date maxDate() {
        DateTime now = new DateTime();
        return now.minusYears(this.max).toDate();
    }

    public static AgeGroup getGroupByBirhday(Date birthday) {
        Years years = Years.yearsBetween(new DateTime(birthday), new DateTime());
        for (AgeGroup ageGroup : values()) {
            int age = years.getYears();
            if(age >=ageGroup.getMin() && age <=ageGroup.getMax()){
                return ageGroup;
            }
        }
        return null;
    }

    public String label() {
        return label;
    }
}

