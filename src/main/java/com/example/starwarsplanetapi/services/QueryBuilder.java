package com.example.starwarsplanetapi.services;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.example.starwarsplanetapi.domain.Planet;

public class QueryBuilder {
    private QueryBuilder() {
    }

    public static Example<Planet> makeQuery(Planet planet) {
        ExampleMatcher queryMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();

        return Example.of(planet, queryMatcher);
    }
}
