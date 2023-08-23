package com.example.starwarsplanetapi.services;

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
}
