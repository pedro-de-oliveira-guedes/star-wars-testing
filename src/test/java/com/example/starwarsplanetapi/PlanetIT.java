package com.example.starwarsplanetapi;

import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET;
import static com.example.starwarsplanetapi.common.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.starwarsplanetapi.domain.Planet;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/remove_planets.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/insert_planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PlanetIT {
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    @Test
    public void createPlanet_ReturnsCreated() {
        Planet createdPlanet = webTestClient.post().uri("/planets").bodyValue(PLANET).exchange()
                .expectStatus().isCreated().expectBody(Planet.class).returnResult().getResponseBody();

        assertThat(createdPlanet).isNotNull();
        assertThat(createdPlanet.getId()).isNotNull();
        assertThat(createdPlanet.getName()).isEqualTo(PLANET.getName());
        assertThat(createdPlanet.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(createdPlanet.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ReturnsPlanet() {
        Planet foundPlanet = webTestClient.get().uri("/planets/1").exchange().expectStatus().isOk()
                .expectBody(Planet.class).returnResult().getResponseBody();

        assertThat(foundPlanet).isNotNull();
        assertThat(foundPlanet.getId()).isEqualTo(TATOOINE.getId());
        assertThat(foundPlanet.getName()).isEqualTo(TATOOINE.getName());
        assertThat(foundPlanet.getClimate()).isEqualTo(TATOOINE.getClimate());
        assertThat(foundPlanet.getTerrain()).isEqualTo(TATOOINE.getTerrain());
    }

    @Test
    public void getPlanetByName_ReturnsPlanet() {
        Planet foundPlanet = webTestClient.get().uri("/planets/name/{name}", TATOOINE.getName()).exchange()
                .expectStatus().isOk().expectBody(Planet.class).returnResult().getResponseBody();

        assertThat(foundPlanet).isNotNull();
        assertThat(foundPlanet.getId()).isEqualTo(TATOOINE.getId());
        assertThat(foundPlanet.getName()).isEqualTo(TATOOINE.getName());
        assertThat(foundPlanet.getClimate()).isEqualTo(TATOOINE.getClimate());
        assertThat(foundPlanet.getTerrain()).isEqualTo(TATOOINE.getTerrain());
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        Planet[] foundPlanets = webTestClient.get().uri("/planets").exchange().expectStatus().isOk()
                .expectBody(Planet[].class).returnResult().getResponseBody();

        assertThat(foundPlanets).isNotNull();
        assertThat(foundPlanets.length).isEqualTo(3);
    }

    @Test
    public void listPlanets_ByClimate_ReturnsPlanets() {
        Planet[] foundPlanets = webTestClient.get().uri("/planets?climate=arid").exchange().expectStatus().isOk()
                .expectBody(Planet[].class).returnResult().getResponseBody();

        assertThat(foundPlanets).isNotNull();
        assertThat(foundPlanets.length).isEqualTo(1);
    }

    @Test
    public void listPlanets_ByTerrain_ReturnsPlanets() {
        Planet[] foundPlanets = webTestClient.get().uri("/planets?terrain=desert").exchange().expectStatus().isOk()
                .expectBody(Planet[].class).returnResult().getResponseBody();

        assertThat(foundPlanets).isNotNull();
        assertThat(foundPlanets.length).isEqualTo(1);
    }

    @Test
    public void removePlanet_ReturnsNoContent() {
        webTestClient.delete().uri("/planets/1").exchange().expectStatus().isNoContent();
    }
}
