package com.superhero.superheroservice.controller;

import com.superhero.superheroservice.service.SuperheroService;
import com.superhero.superheroservice.timed.Timed;
import com.superhero.superheroservice.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Superhero> getSuperheroById(@PathVariable Long id) {
        Superhero superhero = superheroService.getSuperheroById(id);
        if (superhero == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(superhero);
    }
    //show all superheroes with a given name (case insensitive) 
    @GetMapping("/search")
    @Timed
    public List<Superhero> getSuperheroesByName(@RequestParam String name) {
        Optional.ofNullable(name)
                .filter(n -> !n.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Name cannot be null or empty"));

        return superheroService.getSuperheroesByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Timed
    public ResponseEntity<Superhero> createSuperhero(@RequestBody Superhero superhero) {
        Optional.ofNullable(superhero)
            .orElseThrow(() -> new IllegalArgumentException("Superhero object cannot be null"));
        Optional.ofNullable(superhero.getName())
            .filter(name -> !name.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("Superhero name cannot be null or empty"));
        
        Superhero createdSuperhero = superheroService.createSuperhero(superhero);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuperhero);
    }

    @PutMapping("/{id}")
    @Timed
    public ResponseEntity<Superhero> updateSuperhero(@PathVariable Long id, @RequestBody Superhero superhero) {
        Optional.ofNullable(id)
            .filter(i -> i > 0)
            .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        Optional.ofNullable(superhero)
            .orElseThrow(() -> new IllegalArgumentException("Superhero object cannot be null"));
        Optional.ofNullable(superhero.getName())
            .filter(name -> !name.isBlank())
            .orElseThrow(() -> new IllegalArgumentException("Superhero name cannot be null or empty"));

        Superhero existingSuperhero = superheroService.getSuperheroById(id);
        if (existingSuperhero == null) {
            return ResponseEntity.notFound().build();
        }

        Superhero updatedSuperhero = superheroService.updateSuperhero(id, superhero);
        return ResponseEntity.ok(updatedSuperhero);
        
    }

    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuperhero(@PathVariable Long id) {
        Superhero existingSuperhero = superheroService.getSuperheroById(id);
        
        Optional.ofNullable(existingSuperhero)
            .orElseThrow(() -> new IllegalArgumentException("Superhero with ID " + id + " does not exist"));

        superheroService.deleteSuperhero(id);
        return ResponseEntity.ok().build();
    }

}