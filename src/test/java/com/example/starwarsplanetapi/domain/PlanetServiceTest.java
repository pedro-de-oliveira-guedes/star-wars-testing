package com.example.starwarsplanetapi.domain;

import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET;
import static com.example.starwarsplanetapi.common.PlanetConstants.PLANETS;
import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET_QUERY;
import static com.example.starwarsplanetapi.common.PlanetConstants.INVALID_PLANET;
import static com.example.starwarsplanetapi.common.PlanetConstants.VALID_ID;
import static com.example.starwarsplanetapi.common.PlanetConstants.VALID_PLANET;
import static com.example.starwarsplanetapi.common.PlanetConstants.INVALID_ID;
import static com.example.starwarsplanetapi.common.PlanetConstants.EXISTENT_NAME;
import static com.example.starwarsplanetapi.common.PlanetConstants.UNEXISTENT_NAME;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanetById_WithValidId_ReturnsPlanet() {
        when(planetRepository.findById(VALID_ID)).thenReturn(VALID_PLANET);

        Optional<Planet> foundPlanet = planetService.get(VALID_ID);

        assertThat(foundPlanet).isNotEmpty();
        assertThat(foundPlanet).isEqualTo(VALID_PLANET);
    }

    @Test
    public void getPlanetById_WithInvalidId_ReturnsEmpty() {
        when(planetRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        Optional<Planet> foundPlanet = planetService.get(INVALID_ID);

        assertThat(foundPlanet).isEmpty();
    }

    @Test
    public void getPlanet_ByExistentName_ReturnsPlanet() {
        when(planetRepository.findByName(EXISTENT_NAME)).thenReturn(VALID_PLANET);

        Optional<Planet> foundPlanet = planetService.findByName(EXISTENT_NAME);

        assertThat(foundPlanet).isNotEmpty();
        assertEquals(VALID_PLANET, foundPlanet);
    }

    @Test
    public void getPlanet_ByUnexistentName_ReturnsEmpty() {
        when(planetRepository.findByName(UNEXISTENT_NAME)).thenReturn(Optional.empty());

        Optional<Planet> foundPlanet = planetService.findByName(UNEXISTENT_NAME);

        assertThat(foundPlanet).isEmpty();
    }

    @Test
    public void listPlanets_WithNoFilters_ReturnsAllPlanets() {
        when(planetRepository.findAll(PLANET_QUERY)).thenReturn(PLANETS);

        List<Planet> planetList = planetService.list("c", "t");

        assertThat(planetList).isNotEmpty();
        assertThat(planetList.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_WithFiltersThatHaveNoMatch_ReturnsNoPlanets() {
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> planetList = planetService.list("c", "t");

        assertThat(planetList).isEmpty();
    }

    @Test
    public void deletePlanet_WithExistentId_DoesNotThrowsError() {
        assertDoesNotThrow( () -> planetService.deleteById(VALID_ID) );
    }

    @Test
    public void deletePlanet_WithNonExistentId_ThrowsError() {
        doThrow(RuntimeException.class).when(planetRepository).deleteById(INVALID_ID);
        
        assertThatThrownBy( () -> planetService.deleteById(INVALID_ID) ).isNotNull();
    }
}
