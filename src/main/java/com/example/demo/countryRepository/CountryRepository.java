package com.example.demo.countryRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
