package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.LabelValue;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.jpa.BrandDao;
import fr.k2i.adbeback.dao.jpa.CityRepository;
import fr.k2i.adbeback.dao.jpa.CountryRepository;
import fr.k2i.adbeback.webapp.bean.BrandBean;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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

    @Autowired
    private IBrandDao brandDao;

    @Value("${addonf.ads.location:/videos/}")
    private String pathAds;

    @Value("${addonf.logo.location:/logos/}")
    private String pathLogo;



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


    @RequestMapping("/getBrands")
    public  @ResponseBody List<BrandBean> getBrands(){
        List<BrandBean> res = new ArrayList<BrandBean>();
        List<Brand> all = brandDao.getAll();
        for (Brand brand : all) {
            res.add(new BrandBean(brand.getId(),brand.getName(),brand.getLogo()));
        }
        return res;
    }


    @RequestMapping(value = "/logo/{filename}.{ext}", method = RequestMethod.GET)
    public void streamLogoAd(@PathVariable String filename,@PathVariable String ext,HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream outputStream = response.getOutputStream();

        File file = new File(pathLogo+filename+"."+ext);

        FileInputStream fileInputStream = new FileInputStream(file);
        int read =0;
        byte []b = new byte[1024];
        while((read = fileInputStream.read(b, 0, 1024))>0){
            outputStream.write(b, 0, read);
            b = new byte[1024];
        }
        fileInputStream.close();

    }
}
