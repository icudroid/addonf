package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.country.Country;
import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 23/01/14
 * Time: 16:45
 * Goal:
 */
@Data
public class CountryBean implements Serializable{
    private Long id;
    private String code;

    public CountryBean(){}
    public CountryBean(Country country) {
        id = country.getId();
        code = country.getCode();
    }
}
