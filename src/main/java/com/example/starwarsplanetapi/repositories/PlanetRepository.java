package com.example.starwarsplanetapi.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.example.starwarsplanetapi.domain.Planet;

import java.util.List;
import java.util.Optional;


public interface PlanetRepository extends CrudRepository<Planet, Long>, QueryByExampleExecutor<Planet> {
    public Optional<Planet> findByName(String name);

    @Override
    <S extends Planet> List<S> findAll(Example<S> query);
}
