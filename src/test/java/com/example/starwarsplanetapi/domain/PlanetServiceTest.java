package com.example.starwarsplanetapi.domain;

import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.starwarsplanetapi.repositories.PlanetRepository;
import com.example.starwarsplanetapi.services.PlanetService;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;
    
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet createdPlanet = planetService.create(PLANET);

        assertThat(createdPlanet).isEqualTo(PLANET);
    }
}