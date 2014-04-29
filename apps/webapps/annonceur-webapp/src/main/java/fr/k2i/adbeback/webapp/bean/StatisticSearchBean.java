package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.statistic.AdViewedType;
import lombok.Data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dimitri
 * Date: 30/01/14
 * Time: 16:58
 * Goal:
 */
@Data
public class StatisticSearchBean implements Serializable{
    private String start ="";
    private String end="";

    private Long idAd = -1L;
    private Long serviceId = -1L;

    private AdViewedType adViewedType =AdViewedType.VIEW;

    private List<AgeGroup> ageGroups = new ArrayList<AgeGroup>();

    private List<Sex> sexes = new ArrayList<Sex>();

    public Date getStartAsDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(start);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date getEndAsDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(end);
        } catch (ParseException e) {
            return null;
        }
    }

}
