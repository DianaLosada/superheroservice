package com.superhero.superheroservice.service;

import com.superhero.superheroservice.model.Superhero;
import com.superhero.superheroservice.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SuperheroService {
    @Autowired
    private SuperheroRepository superheroRepository;

    public List<Superhero> getAllSuperheroes() {
        return superheroRepository.findAll();
    }

    public Superhero getSuperheroById(Long id) {
        return superheroRepository.findById(id).orElse(null);
    }

    public List<Superhero> getSuperheroesByName(String name) {
        return superheroRepository.findByNameIgnoreCaseContaining(name);
    }

    public Superhero createSuperhero(Superhero superhero) {
        return superheroRepository.save(superhero);
    }

    public Superhero updateSuperhero(Long id, Superhero superhero) {
        if (superheroRepository.existsById(id)) {
            superhero.setName(superhero.getName());
            return superheroRepository.save(superhero);
        }
        return null;
    }

    public void deleteSuperhero(Long id) {
        superheroRepository.deleteById(id);
    }
}



