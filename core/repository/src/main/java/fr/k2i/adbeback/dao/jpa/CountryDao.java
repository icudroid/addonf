package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.country.Country;
import org.springframework.stereotype.Repository;


@Repository("countryDao")
public interface CountryDao extends org.springframework.data.repository.Repository<Country, Long> {
	public Country findByCode(String code);
}

