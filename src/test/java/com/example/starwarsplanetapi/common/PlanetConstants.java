package com.example.starwarsplanetapi.common;

import java.util.Optional;

import com.example.starwarsplanetapi.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");

    public static final Planet INVALID_PLANET = new Planet("", "", "");

    public static final Long VALID_ID = 1L;
    public static final Optional<Planet> VALID_PLANET = Optional.of(PLANET);

    public static final Long INVALID_ID = 2L;
}
