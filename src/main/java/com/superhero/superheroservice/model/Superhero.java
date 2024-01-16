package com.superhero.superheroservice.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a superhero entity in the application.
 */
@Entity
@Table(name = "superheroes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Superhero implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * Constructs a superhero object with the given name.
     * 
     * @param name the name of the superhero
     */
    public Superhero(String name) {
        this.name = name;
    }
}


