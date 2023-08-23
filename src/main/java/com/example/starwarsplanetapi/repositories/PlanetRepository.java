package com.example.starwarsplanetapi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.starwarsplanetapi.domain.Planet;

public interface PlanetRepository extends CrudRepository<Planet, Long> {

}
