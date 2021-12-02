package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.beans.Country;
import com.example.demo.countryController.CountryController;
import com.example.demo.countryService.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.example.demo")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { ControllerMockMVCests.class })
public class ControllerMockMVCests {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test
	@Order(1)
	public void test_getCountries() throws Exception {
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));

		when(countryService.getAllCountries()).thenReturn(mycountries);

		this.mockMvc.perform(get("/getcountries")).andExpect(status().isFound()).andDo(print());
	}

	@Test
	@Order(2)
	public void test_getCountryById() throws Exception {

		Country country = new Country(2, "Uk", "London");
		int countryId = 3;

		when(countryService.getCountryById(countryId)).thenReturn(country);

		this.mockMvc.perform(get("/getcountries/{id}", countryId)).andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Uk"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London")).andDo(print());
	}

	@Test
	@Order(3)
	public void test_getCountryByName() throws Exception {

		Country country = new Country(2, "Uk", "London");
		String countryName = "Uk";

		when(countryService.getCountryByName(countryName)).thenReturn(country);

		this.mockMvc.perform(get("/getcountry/countryname").param("name", "Uk")).andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Uk"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("London")).andDo(print());
	}

	@Test
	@Order(4)
	public void test_addCountry() throws Exception {

		Country country = new Country(2, "Uk", "London");

		when(countryService.addCountry(country)).thenReturn(country);

		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(country);

		this.mockMvc.perform(post("/addcountry").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	@Order(5)
	public void test_updateCountry() throws Exception {

		Country country = new Country(3, "Japan", "Tokyo");
		int countryId = 3;

		when(countryService.getCountryById(countryId)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);

		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(country);

		this.mockMvc
				.perform(
						put("/updatecountry/{id}", countryId).content(jsonbody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo")).andDo(print());
	}

	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception {

		Country country = new Country(3, "Japan", "Tokyo");
		int countryId = 3;

		when(countryService.getCountryById(countryId)).thenReturn(country);

		this.mockMvc.perform(delete("/deletecountry/{id}", countryId)).andExpect(status().isOk());

	}
}
