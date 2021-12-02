package com.example.demo;

import static org.junit.Assert.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.beans.Country;
import com.example.demo.countryController.CountryController;
import com.example.demo.countryService.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { ControllerMockitoTests.class })
public class ControllerMockitoTests {

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countrycontroller;

	List<Country> mycountries;
	Country country;

	@Test
	@Order(1)
	public void test_getAllcountries() {

		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));

		when(countryService.getAllCountries()).thenReturn(mycountries); // Mocking

		ResponseEntity<List<Country>> res = countrycontroller.getAllCountries();

		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(2, res.getBody().size());

	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		country = new Country(2, "Uk", "London");

		int countryId = 2;

		when(countryService.getCountryById(countryId)).thenReturn(country); // Mocking

		ResponseEntity<Country> res = countrycontroller.getCountryById(countryId);

		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(country.getId(), res.getBody().getId());
		assertEquals(country.getCountryName(), res.getBody().getCountryName());
		assertEquals(country.getCountryCapital(), res.getBody().getCountryCapital());

	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		country = new Country(2, "Uk", "London");

		String countryName = "Uk";

		when(countryService.getCountryByName(countryName)).thenReturn(country); // Mocking

		ResponseEntity<Country> res = countrycontroller.getCountryByName(countryName);

		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(country.getId(), res.getBody().getId());
		assertEquals(country.getCountryName(), res.getBody().getCountryName());
		assertEquals(country.getCountryCapital(), res.getBody().getCountryCapital());

	}

	@Test
	@Order(4)
	public void test_addCountry() {
		country = new Country(2, "Uk", "London");

		when(countryService.addCountry(country)).thenReturn(country); // Mocking

		ResponseEntity<Country> res = countrycontroller.addCountry(country);

		assertEquals(HttpStatus.CREATED, res.getStatusCode());
		assertEquals(country, res.getBody());

	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		country = new Country(2, "Uk", "London");
		int countryId = 2;

		when(countryService.getCountryById(countryId)).thenReturn(country); // Mocking
		when(countryService.updateCountry(country)).thenReturn(country); // Mocking
		ResponseEntity<Country> res = countrycontroller.updateCountry(countryId, country);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(2, res.getBody().getId());
		assertEquals("Uk", res.getBody().getCountryName());
		assertEquals("London", res.getBody().getCountryCapital());

	}

	@Test
	@Order(6)
	public void test_delateCountry() {
		country = new Country(2, "Uk", "London");
		int countryId = 2;

		when(countryService.getCountryById(countryId)).thenReturn(country); // Mocking
		ResponseEntity<Country> res = countrycontroller.deleteCountry(countryId);

		assertEquals(HttpStatus.OK, res.getStatusCode());

	}
}
