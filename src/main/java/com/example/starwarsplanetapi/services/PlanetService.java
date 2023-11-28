package com.example.starwarsplanetapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.starwarsplanetapi.domain.Planet;
import com.example.starwarsplanetapi.repositories.PlanetRepository;

@Service
public class PlanetService {
    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    public Optional<Planet> get(Long id) {
        return planetRepository.findById(id);
    }

    public Optional<Planet> findByName(String planetName) {
        return planetRepository.findByName(planetName);
    }

    public List<Planet> list(String climate, String terrain) {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));

        return planetRepository.findAll(query);
    }

    public void deleteById(Long id) {
        planetRepository.deleteById(id);
    }
}
