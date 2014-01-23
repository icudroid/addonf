package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.country.City;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 23/01/14
 * Time: 16:41
 * Goal:
 */
@Data
public class CityBean implements Serializable {
    private Long id;
    private String zipcode;
    private String city;

    public CityBean(){}
    public CityBean(City city) {
        id = city.getId();
        zipcode = city.getZipcode();
        this.city = city.getCity();
    }
}
