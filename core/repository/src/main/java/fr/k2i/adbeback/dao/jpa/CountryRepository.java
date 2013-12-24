package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.country.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("countryRepository")
public interface CountryRepository extends CrudRepository<Country, Long> {
	public Country findByCode(String code);
}

