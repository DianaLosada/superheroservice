package com.superhero.superheroservice;

import com.superhero.superheroservice.exception.NotFoundSuperheroException;
import com.superhero.superheroservice.model.Superhero;
import com.superhero.superheroservice.repository.SuperheroRepository;
import com.superhero.superheroservice.service.SuperheroService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Integration tests for the SuperheroController class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperheroControllerTests {

    @MockBean
    private SuperheroRepository superheroRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private SuperheroService superheroService;


    @Test
    void shouldCreateNewSuperhero() {
        ResponseEntity<Superhero> superHeroResponse = testRestTemplate.postForEntity("/api/superheroes",
                new Superhero("Batman"), Superhero.class);

        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void createShouldReturnBadRequestWhenNameIsEmpty() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/superheroes",
                new Superhero(""), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createShouldReturnBadRequestWhenSuperheroNameIsNull() {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/superheroes",
                new Superhero(null), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldDeleteSuperhero() {
        Long superheroId = 1L;
        Superhero existingSuperhero = new Superhero("Batman");
        existingSuperhero.setId(superheroId);

        when(superheroService.getSuperheroById(superheroId)).thenReturn(existingSuperhero);

        testRestTemplate.delete("/api/superheroes/" + superheroId);

        when(superheroService.getSuperheroById(superheroId)).thenThrow( new NotFoundSuperheroException("Superhero with id " + superheroId + " not found"));
        ResponseEntity<Superhero> response = testRestTemplate.getForEntity("/api/superheroes/" + superheroId,
                Superhero.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnBadRequestWhenSuperheroDoesNotExist() {
        Long superheroId = 1L;

        when(superheroService.getSuperheroById(superheroId)).thenReturn(null);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/superheroes/" + superheroId,
                HttpMethod.DELETE, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }



    @Test
    void shouldReturnBadRequestWhenSuperheroDoesNotExistForUpdate() {
        Long superheroId = 1L;
        Superhero nonExistingSuperhero = new Superhero("Non-existing Batman");
        nonExistingSuperhero.setId(superheroId);

        when(superheroService.updateSuperhero(superheroId, nonExistingSuperhero)).thenReturn(null);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/superheroes/" + superheroId, HttpMethod.PUT,
                new HttpEntity<>(nonExistingSuperhero), String.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void shouldReturnBadRequestWhenSuperheroNameIsEmpty() {
        Long superheroId = 1L;
        Superhero superheroWithEmptyName = new Superhero("");
        superheroWithEmptyName.setId(superheroId);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/superheroes/" + superheroId, HttpMethod.PUT,
                new HttpEntity<>(superheroWithEmptyName), String.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}