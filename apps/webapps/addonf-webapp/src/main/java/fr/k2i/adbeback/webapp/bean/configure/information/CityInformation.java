package fr.k2i.adbeback.webapp.bean.configure.information;

import lombok.Data;

/**
 * User: dimitri
 * Date: 24/03/14
 * Time: 17:03
 * Goal:
 */
@Data
class CityInformation extends Information {
    private String countryCode;
    private String zipCode;
    private String cityName;
}
