package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.dao.ICityDao;
import fr.k2i.adbeback.dao.ICountryDao;
import fr.k2i.adbeback.logger.LogHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 22/12/13
 * Time: 00:02
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ImportServiceFacade {

    private static Logger logger = LogHelper.getLogger(ImportServiceFacade.class);


    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountryDao countryDao;


    @Transactional
    public void importCities() throws IOException {
        /*CSVReader reader = new CSVReader(new FileReader("/tmp/FR.txt"),'\t');
        String[] row = null;

        Country country = null;


        while((row = reader.readNext()) != null) {
            logger.debug(Arrays.toString(row));
            if(country == null){
                country = countryRepository.findByCode(row[0]);
                if(country ==null){
                    country = new Country();
                    country.setCode(row[0]);
                }
            }else if(!row[0].equals(country.getCode())){
                country = countryRepository.findByCode(row[0]);
                if(country ==null){
                    country = new Country();
                    country.setCode(row[0]);
                }
            }

            List<City> cities = cityRepository.findByZipcodeAndCountry_Code(row[1], country.getCode());
            City city = null;
            if(cities.isEmpty()){
                city = new City();
            }else if(cities.size()==1){
                city = cities.get(0);
            }else{
                city = cityRepository.findByZipcodeAndCityAndCountry_Code(row[1], row[2],country.getCode());
            }

            city.setCity(row[2]);
            city.setZipcode(row[1]);
            try{
            city.setLat(Double.parseDouble(row[9]));
            city.setLon(Double.parseDouble(row[10]));
            }catch (Exception e){

            }
            city.setCountry(country);


            cityRepository.save(city);

        }
//...
        reader.close();*/

    }

}
