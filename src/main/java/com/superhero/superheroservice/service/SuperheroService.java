package com.superhero.superheroservice.service;

import com.superhero.superheroservice.exception.NotFoundSuperheroException;
import com.superhero.superheroservice.model.Superhero;
import com.superhero.superheroservice.repository.SuperheroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * The SuperheroService class provides methods to manage superheroes.
 */
@Service
@Slf4j
public class SuperheroService {
    @Autowired
    private SuperheroRepository superheroRepository;

    /**
     * Retrieves all superheroes from the repository.
     * 
     * @return a list of all superheroes
     */
    @Cacheable(cacheNames="superheroesCache")
    public List<Superhero> getAllSuperheroes() {
        log.info("getAllSuperheroes called");
        return superheroRepository.findAll();
    }

    /**
     * Retrieves a superhero by its ID.
     *
     * @param id the ID of the superhero to retrieve
     * @return the superhero with the specified ID
     * @throws NotFoundSuperheroException if the superhero with the specified ID does not exist
     */
    public Superhero getSuperheroById(Long id) {
        return superheroRepository.findById(id).orElseThrow(()->new NotFoundSuperheroException("Superhero with id " + id + " not found"));
    }

    /**
     * Retrieves superheroes by their name.
     * 
     * @param name the name of the superheroes to search for
     * @return a list of superheroes with names containing the specified name
     */
    @Cacheable(cacheNames="superheroesCache")
    public List<Superhero>  getSuperheroesByName(String name) {
        log.info("getSuperheroesByName called");
        return superheroRepository.findByNameIgnoreCaseContaining(name);
    }

    /**
     * Creates a new superhero.
     * 
     * @param superhero the superhero to create
     * @return the created superhero
     */
    @CacheEvict(cacheNames="superheroesCache", allEntries=true)
    public Superhero createSuperhero(Superhero superhero) {
        return superheroRepository.save(superhero);
    }

    /**
     * Updates an existing superhero.
     * 
     * @param id the ID of the superhero to update
     * @param superhero the updated superhero
     * @return the updated superhero, or null if the superhero with the specified ID does not exist
     */
    @CacheEvict(cacheNames="superheroesCache", allEntries=true)
    public Superhero updateSuperhero(Long id, Superhero superhero) {
        if (superheroRepository.existsById(id)) {
            superhero.setName(superhero.getName());
            return superheroRepository.save(superhero);
        }
        return null;
    }

    /**
     * Deletes a superhero by its ID.
     * 
     * @param id the ID of the superhero to delete
     */
    @CacheEvict(cacheNames="superheroesCache", allEntries=true)
    public void deleteSuperhero(Long id) {
        superheroRepository.deleteById(id);
    }
}



