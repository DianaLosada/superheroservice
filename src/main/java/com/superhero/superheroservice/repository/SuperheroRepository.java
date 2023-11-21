package com.superhero.superheroservice.repository;

import com.superhero.superheroservice.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

    List<Superhero> findByNameIgnoreCaseContaining(String name);

}


