package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("cityRepository")
public interface CityRepository extends CrudRepository<City, Long> {
	public List<City> findByZipcodeAndCountry_Code(String zipcode,String code);

    public City findByZipcodeAndCityAndCountry_Code(String zipcode,String city,String code);
}

