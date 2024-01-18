package com.superhero.superheroservice.controller;

import com.superhero.superheroservice.exception.NotFoundSuperheroException;
import com.superhero.superheroservice.service.SuperheroService;
import com.superhero.superheroservice.timed.Timed;
import com.superhero.superheroservice.model.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

/**
 * The SuperheroController class provides REST endpoints for managing superheroes.
 */
@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {
    private final SuperheroService superheroService;

    @Autowired
    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    /**
     * Get all superheroes.
     *
     * @return List of all superheroes.
     */
    @GetMapping
    @Timed
 public ResponseEntity<Page<Superhero>> getAllSuperheroes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "200") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Superhero> result = superheroService.getAllSuperheroes(pageable);
    return ResponseEntity.ok(result);
}

    /**
     * Get a superhero by ID.
     *
     * @param id The ID of the superhero.
     * @return ResponseEntity containing the superhero if found, or not found status if not found.
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<Superhero> getSuperheroById(@PathVariable Long id) {
        Superhero superhero = null;
        try {
            superhero = superheroService.getSuperheroById(id);
        }catch (NotFoundSuperheroException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(superhero);
    }

    /**
     * Get all superheroes with a given name (case insensitive).
     *
     * @param name The name of the superheroes to search for.
     * @return List of superheroes with the given name.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    @GetMapping("/search")
    @Timed
    public ResponseEntity<Page<Superhero>> getSuperheroesByName(@RequestParam String name,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "200") int size) {
        Optional.ofNullable(name)
                .filter(n -> !n.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Name cannot be null or empty"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Superhero> result = superheroService.getSuperheroesByName(name, pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * Create a new superhero.
     *
     * @param superhero The superhero object to create.
     * @return ResponseEntity containing the created superhero.
     * @throws IllegalArgumentException if the superhero object or name is null or empty.
     */
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

    /**
     * Update a superhero by ID.
     *
     * @param id The ID of the superhero to update.
     * @param superhero The updated superhero object.
     * @return ResponseEntity containing the updated superhero if found, or not found status if not found.
     * @throws IllegalArgumentException if the ID, superhero object, or name is invalid or null.
     */
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

    /**
     * Delete a superhero by ID.
     *
     * @param id The ID of the superhero to delete.
     * @return ResponseEntity with no content if the superhero is deleted successfully.
     * @throws IllegalArgumentException if the superhero with the given ID does not exist.
     */
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