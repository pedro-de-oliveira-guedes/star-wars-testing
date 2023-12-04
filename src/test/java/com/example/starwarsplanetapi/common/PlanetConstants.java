package com.example.starwarsplanetapi.common;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;

import com.example.starwarsplanetapi.domain.Planet;
import com.example.starwarsplanetapi.services.QueryBuilder;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");

    public static final Planet INVALID_PLANET = new Planet("", "", "");

    public static final Long VALID_ID = 1L;
    public static final Optional<Planet> VALID_PLANET = Optional.of(PLANET);

    public static final Long INVALID_ID = 2L;

    public static final String EXISTENT_NAME = "Leporidônea";
    public static final String UNEXISTENT_NAME = "Leoporidônea";

    public static final Example<Planet> PLANET_QUERY = QueryBuilder.makeQuery(new Planet("c", "t"));

    public static final Planet TATOOINE = new Planet("Tatooine", "arid", "desert");
    private static final Planet ALDERAAN = new Planet("Alderaan", "temperate", "grasslands, mountains");
    private static final Planet YAVIN_IV = new Planet("Yavin IV", "temperate, tropical", "jungle, rainforests");
    public static final List<Planet> PLANETS = Arrays.asList(PLANET, TATOOINE, ALDERAAN, YAVIN_IV);
}
