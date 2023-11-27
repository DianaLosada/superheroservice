package com.superhero.superheroservice.controller;

import com.superhero.superheroservice.service.SuperheroService;
import com.superhero.superheroservice.timed.Timed;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {
    private final SuperheroService superheroService;

    @Autowired
    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @GetMapping
    @Timed
    public List<Superhero> getAllSuperheroes() {
        return superheroService.getAllSuperheroes();
    }

    @GetMapping("/{id}")
    @Timed
    public Superhero getSuperheroById(@PathVariable Long id) {
        return superheroService.getSuperheroById(id);
    }
    //show all superheroes with a given name (case insensitive) 
    @GetMapping("/search")
    @Timed
    public List<Superhero> getSuperheroesByName(@RequestParam String name) {
        return superheroService.getSuperheroesByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Timed
    public Superhero createSuperhero(@RequestBody Superhero superhero) {
        Optional.ofNullable(superhero)
            .orElseThrow(() -> new IllegalArgumentException("Superhero object cannot be null"));
        Optional.ofNullable(superhero.getName())
            .filter(name -> !name.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("Superhero name cannot be null or empty"));
        return superheroService.createSuperhero(superhero);
    }

    @PutMapping("/{id}")
    @Timed
    public Superhero updateSuperhero(@PathVariable Long id, @RequestBody Superhero superhero) {
        Optional.ofNullable(id)
            .filter(i -> i > 0)
            .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        Optional.ofNullable(superhero)
            .orElseThrow(() -> new IllegalArgumentException("Superhero object cannot be null"));
        Optional.ofNullable(superhero.getName())
            .filter(name -> !name.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("Superhero name cannot be null or empty"));
            
        return superheroService.updateSuperhero(id, superhero);
    }

    @DeleteMapping("/{id}")
    @Timed
    public void deleteSuperhero(@PathVariable Long id) {
        Superhero existingSuperhero = superheroService.getSuperheroById(id);
        
        Optional.ofNullable(existingSuperhero)
            .orElseThrow(() -> new IllegalArgumentException("Superhero with ID " + id + " does not exist"));
  
        superheroService.deleteSuperhero(id);
    }

}