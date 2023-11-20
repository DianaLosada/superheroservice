package com.superhero.superheroservice.controller;

import com.superhero.superheroservice.service.SuperheroService;
import com.superhero.superheroservice.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {
    private final SuperheroService superheroService;

    @Autowired
    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }
    @GetMapping
    public List<Superhero> getAllSuperheroes() {
        return superheroService.getAllSuperheroes();
    }

    @GetMapping("/{id}")
    public Superhero getSuperheroById(@PathVariable Long id) {
        return superheroService.getSuperheroById(id);
    }

    @GetMapping("/search")
    public List<Superhero> getSuperheroesByName(@RequestParam String name) {
        return superheroService.getSuperheroesByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero createSuperhero(@RequestBody Superhero superhero) {
        return superheroService.createSuperhero(superhero);
    }

    @PutMapping("/{id}")
    public Superhero updateSuperhero(@PathVariable Long id, @RequestBody Superhero superhero) {
        return superheroService.updateSuperhero(id, superhero);
    }

    @DeleteMapping("/{id}")
    public void deleteSuperhero(@PathVariable Long id) {
        superheroService.deleteSuperhero(id);
    }
}