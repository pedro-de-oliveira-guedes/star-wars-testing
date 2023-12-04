package com.example.starwarsplanetapi.web;

import static com.example.starwarsplanetapi.common.PlanetConstants.PLANET;
import static com.example.starwarsplanetapi.common.PlanetConstants.INVALID_PLANET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.starwarsplanetapi.domain.Planet;
import com.example.starwarsplanetapi.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(
            post("/planets").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(PLANET))
        )
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(PLANET)));
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
        // Testing with invalid planet
        mockMvc.perform(
            post("/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(INVALID_PLANET))
        )
            .andExpect(status().isUnprocessableEntity());

        // Testing with empty planet
        mockMvc.perform(
            post("/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new Planet()))
        )
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsBadRequest() throws Exception {
        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(
            post("/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PLANET))
        )
            .andExpect(status().isConflict());
    }
}
