package com.superhero.superheroservice.repository;

import com.superhero.superheroservice.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Superhero entities.
 */
@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

    /**
     * Finds a list of superheroes whose name contains the specified case-insensitive substring.
     *
     * @param name the substring to search for in the superhero names
     * @return a list of superheroes matching the search criteria
     */
    List<Superhero> findByNameIgnoreCaseContaining(String name);

}


