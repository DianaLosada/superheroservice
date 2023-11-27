package com.superhero.superheroservice;

import com.superhero.superheroservice.model.Superhero;
import com.superhero.superheroservice.repository.SuperheroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperheroControllerTests {

    @MockBean
    private SuperheroRepository superheroRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateNewSuperhero() {

        ResponseEntity<Superhero> superHeroResponse = testRestTemplate.postForEntity("/api/superheroes",
                new Superhero("Batman"), Superhero.class);

        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnSuperheroesListWhenExists() {
        final List<Superhero> superheroesList = new ArrayList<>();
        Superhero superhero1 = new Superhero();
        superhero1.setName("Batman");
        superheroesList.add(superhero1);

        // given
        given(superheroRepository.findByNameIgnoreCaseContaining("Batman"))
                .willReturn(superheroesList);

        // when
        ResponseEntity<Superhero[]> superHeroResponse = testRestTemplate.getForEntity("/api/superheroes/search?name=Batman", Superhero[].class);
        Superhero[] superheroes = superHeroResponse.getBody();

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Superhero superhero =  Arrays.stream(superheroes).findFirst().orElse(null);
        if(superhero != null) {
            assertThat(superhero.getName()).isEqualTo("Batman");
        }
    }

    @Test
    void shouldReturnSuperheroesListEmptyWhenNotExists() {
        final List<Superhero> superheroesList = new ArrayList<>();
        // given
        given(superheroRepository.findByNameIgnoreCaseContaining("Batman") )
                .willReturn(superheroesList);

        // when
        ResponseEntity<Superhero[]> superHeroResponse = testRestTemplate.getForEntity("/api/superheroes/search?name=Batman", Superhero[].class);
        Superhero[] superheroes = superHeroResponse.getBody();
        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(superheroes).isEmpty();
    }
}