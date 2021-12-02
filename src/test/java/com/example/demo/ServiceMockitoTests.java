package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.beans.Country;
import com.example.demo.countryRepository.CountryRepository;
import com.example.demo.countryService.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { ServiceMockitoTests.class })
public class ServiceMockitoTests {

	@Mock
	CountryRepository countryRepo;

	@InjectMocks
	CountryService countryService;

	List<Country> mycountries;
	Country country;

	@Test
	@Order(1)
	public void test_getcountries() {

		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));

		when(countryRepo.findAll()).thenReturn(mycountries);
		assertEquals(2, countryService.getAllCountries().size());

	}

	@Test
	@Order(2)
	public void test_getcountryById() {

		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));

		int countryId = 2;

		when(countryRepo.findAll()).thenReturn(mycountries);

		assertEquals(countryId, countryService.getCountryById(countryId).getId());
	}

	@Test
	@Order(3)
	public void test_getcountryByName() {

		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));

		String countryName = "India";

		when(countryRepo.findAll()).thenReturn(mycountries);

		assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
	}

	@Test
	@Order(4)
	public void test_addCountry() {

		country = new Country(3, "Japan", "Tokyo");

		when(countryRepo.save(country)).thenReturn(country);
		assertEquals(country, countryService.addCountry(country));

	}

	@Test
	@Order(5)
	public void test_updateCountry() {

		country = new Country(3, "Japan", "Tokyo");

		when(countryRepo.save(country)).thenReturn(country);
		assertEquals(country, countryService.updateCountry(country));
	}

	@Test
	@Order(6)
	public void test_deleteCountry() {

		country = new Country(3, "Japan", "Tokyo");
		countryService.deleteCountry(country);

		verify(countryRepo, times(1)).delete(country);
	}
}
