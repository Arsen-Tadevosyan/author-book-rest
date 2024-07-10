package com.example.authorbookrest.repository;


import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

}
