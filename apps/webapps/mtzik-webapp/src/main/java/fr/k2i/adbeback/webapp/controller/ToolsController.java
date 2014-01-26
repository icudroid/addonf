package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.LabelValue;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.jpa.CityRepository;
import fr.k2i.adbeback.dao.jpa.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 22/12/13
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ToolsController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MessageSource messageSource;



    @RequestMapping("/getTowns/{country}/{postalCode}")
    public @ResponseBody
    List<String> getTowns(@PathVariable("country") String country, @PathVariable("postalCode") String postalCode) {
        List<City> cities = cityRepository.findByZipcodeAndCountry_Code(postalCode, country);
        List<String> res = new ArrayList<String>();
        for (City city : cities) {
            res.add(city.getCity());
        }
        return res;
    }


    @RequestMapping("/getCountries")
    public @ResponseBody
    List<LabelValue> getCountries(HttpServletRequest request) {
        Iterable<Country> iterable = countryRepository.findAll();

        List<LabelValue> res = new ArrayList<LabelValue>();
        for (Country country : iterable) {
            res.add(new LabelValue(messageSource.getMessage("country."+country.getCode(),new Object[]{},request.getLocale()),country.getCode()));
        }
        return res;
    }


    @RequestMapping("/getTownsByName/{city}")
    public @ResponseBody
    List<City> getTownsByName(@PathVariable("city") String city) {
        return cityRepository.findByCityStartingWith(city);
    }



    @RequestMapping("/getSexes")
    public @ResponseBody
    List<LabelValue> getSexes(HttpServletRequest request) {
        List<LabelValue> res = new ArrayList<LabelValue>();
        for (Sex sex : Sex.values()) {
            res.add(new LabelValue(messageSource.getMessage("sex."+sex.name(),new Object[]{},request.getLocale()),sex.name()));
        }
        return res;
    }

}
