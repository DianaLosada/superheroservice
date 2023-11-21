package com.superhero.superheroservice.service;

import com.superhero.superheroservice.model.Superhero;
import com.superhero.superheroservice.repository.SuperheroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class SuperheroService {
    @Autowired
    private SuperheroRepository superheroRepository;
    @Cacheable(cacheNames="superheroesCache")
    public List<Superhero> getAllSuperheroes() {
        log.info("getAllSuperheroes called");
        return superheroRepository.findAll();
    }

    public Superhero getSuperheroById(Long id) {
        return superheroRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames="superheroesCache")
    public List<Superhero> getSuperheroesByName(String name) {
        log.info("getSuperheroesByName called");
        return superheroRepository.findByNameIgnoreCaseContaining(name);
    }

    @CacheEvict(cacheNames="superheroesCache", allEntries=true)
    public Superhero createSuperhero(Superhero superhero) {
        return superheroRepository.save(superhero);
    }

    @CacheEvict(cacheNames="superheroesCache", allEntries=true)
    public Superhero updateSuperhero(Long id, Superhero superhero) {
        if (superheroRepository.existsById(id)) {
            superhero.setName(superhero.getName());
            return superheroRepository.save(superhero);
        }
        return null;
    }

    @CacheEvict(cacheNames="superheroesCache", allEntries=true)
    public void deleteSuperhero(Long id) {
        superheroRepository.deleteById(id);
    }
}



