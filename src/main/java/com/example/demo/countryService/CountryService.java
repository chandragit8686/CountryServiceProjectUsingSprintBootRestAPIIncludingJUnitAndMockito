package com.example.demo.countryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Country;
import com.example.demo.countryRepository.CountryRepository;

@Component
@Service
public class CountryService {

	@Autowired
	CountryRepository countryRepo;

	public List<Country> getAllCountries() {

		List<Country> countries = countryRepo.findAll();
		return countries;

	}

	public Country getCountryById(int id) {

		List<Country> countries = countryRepo.findAll();
		Country country = null;
		for (Country con : countries) {
			if (con.getId() == id)
				country = con;
		}
		return country;

	}

	public Country getCountryByName(String countryname) {

		List<Country> countries = countryRepo.findAll();
		Country country = null;
		for (Country con : countries) {
			if (con.getCountryName().equalsIgnoreCase(countryname))
				country = con;
		}
		return country;

	}

	public Country addCountry(Country country) {

		country.setId(getMaxId());
		countryRepo.save(country);
		return country;
	}

	public int getMaxId() {
		return countryRepo.findAll().size() + 1;
	}

	public Country updateCountry(Country country) {
		countryRepo.save(country);
		return country;

	}

	public void deleteCountry(Country country) {
		countryRepo.delete(country);

	}

}
