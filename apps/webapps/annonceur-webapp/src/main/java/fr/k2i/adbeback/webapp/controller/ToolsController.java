package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.dao.jpa.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
