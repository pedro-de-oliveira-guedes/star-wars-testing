package com.example.starwarsplanetapi.domain;

import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.starwarsplanetapi.repositories.PlanetRepository;
import com.example.starwarsplanetapi.services.PlanetService;

@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {
    @Autowired
    private PlanetService planetService;

    @MockBean
    private PlanetRepository planetRepository;
    
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet createdPlanet = planetService.create(PLANET);

        assertThat(createdPlanet).isEqualTo(PLANET);
    }
}
