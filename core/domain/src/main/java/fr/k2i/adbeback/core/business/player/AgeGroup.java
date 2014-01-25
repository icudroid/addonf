package fr.k2i.adbeback.core.business.player;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 24/01/14
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public enum AgeGroup{
    $15_17(15,17),
    $18_24(18,24),
    $25_34(25,34),
    $35_49(35,49),
    $50_64(50,64),
    _65_MORE(65,130);

    private int min;
    private int max;

    private AgeGroup(int min, int max) {
        this.min = min;
        this.max = max;
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
}

