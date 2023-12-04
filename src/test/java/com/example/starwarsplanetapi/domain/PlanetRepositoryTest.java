package com.example.starwarsplanetapi.domain;

import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.starwarsplanetapi.repositories.PlanetRepository;

@DataJpaTest
public class PlanetRepositoryTest {
    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void tearDown() {
        planetRepository.deleteAll();
        PLANET.setId(null);
    }

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        Planet createdPlanet = planetRepository.save(PLANET);

        Planet foundPlanet = testEntityManager.find(Planet.class, createdPlanet.getId());

        assertThat(foundPlanet).isNotNull();
        assertThat(foundPlanet.getName()).isEqualTo(PLANET.getName());
        assertThat(foundPlanet.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(foundPlanet.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Planet foundPlanet = planetRepository.findById(planet.getId()).orElse(null);

        assertThat(foundPlanet).isNotNull();
        assertThat(foundPlanet.getName()).isEqualTo(PLANET.getName());
        assertThat(foundPlanet.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(foundPlanet.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ByNonExistingId_ReturnsNull() {
        Planet foundPlanet = planetRepository.findById(1L).orElse(null);

        assertThat(foundPlanet).isNull();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Planet foundPlanet = planetRepository.findByName(planet.getName()).orElse(null);

        assertThat(foundPlanet).isNotNull();
        assertThat(foundPlanet.getName()).isEqualTo(PLANET.getName());
        assertThat(foundPlanet.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(foundPlanet.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ByNonExistingName_ReturnsNull() {
        Planet foundPlanet = planetRepository.findByName("Non existing name").orElse(null);

        assertThat(foundPlanet).isNull();
    }
}