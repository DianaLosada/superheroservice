package com.superhero.superheroservice.repository;

import com.superhero.superheroservice.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

    List<Superhero> findByNameIgnoreCaseContaining(String name);

}


