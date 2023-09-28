package com.example.starwarsplanetapi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.starwarsplanetapi.domain.Planet;
import java.util.Optional;


public interface PlanetRepository extends CrudRepository<Planet, Long> {
    public Optional<Planet> findByName(String name);
}
