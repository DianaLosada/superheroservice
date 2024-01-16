package com.superhero.superheroservice;

import com.superhero.superheroservice.controller.SuperheroController;
import com.superhero.superheroservice.exception.NotFoundSuperheroException;
import com.superhero.superheroservice.model.Superhero;
import com.superhero.superheroservice.service.SuperheroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;

/**
 * Unit tests for the SuperheroController class.
 */
@ExtendWith(MockitoExtension.class)
public class SuperheroControllerUnitTests {

    @Mock
    private SuperheroService superheroService;

    @InjectMocks
    private SuperheroController superheroController;

    @Test
    void testGetSuperheroById() {
        Superhero superhero = new Superhero();
        superhero.setName("Superman");
        superhero.setId(1L);

        when(superheroService.getSuperheroById(anyLong())).thenReturn(superhero);

        ResponseEntity<Superhero> result = superheroController.getSuperheroById(1L);

        assertThat(result)
                .isNotNull()
                .extracting(ResponseEntity::getBody)
                .isNotNull()
                .extracting(Superhero::getName)
                .isEqualTo(superhero.getName());

        verify(superheroService, times(1)).getSuperheroById(1L);
    }

    @Test
    void testGetSuperheroByIdNotFound() {
        when(superheroService.getSuperheroById(anyLong())).thenThrow(new NotFoundSuperheroException("Superhero with id 1 not found"));

        ResponseEntity<Superhero> result = superheroController.getSuperheroById(1L);

        assertThat(result)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.NOT_FOUND);

        verify(superheroService, times(1)).getSuperheroById(1L);
    }

    @Test
    void testUpdateSuperhero_ValidIdAndSuperhero_ReturnsOkResponse() {
        // Arrange
        Long id = 1L;
        Superhero superhero = new Superhero();
        superhero.setName("Superman");

        when(superheroService.getSuperheroById(id)).thenReturn(new Superhero());
        when(superheroService.updateSuperhero(id, superhero)).thenReturn(superhero);

        // Act
        ResponseEntity<Superhero> response = superheroController.updateSuperhero(id, superhero);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(superhero, response.getBody());
        verify(superheroService, times(1)).getSuperheroById(id);
        verify(superheroService, times(1)).updateSuperhero(id, superhero);
    }

    @Test
    void testUpdateSuperhero_InvalidId_ThrowsIllegalArgumentException() {
        // Arrange
        Long id = -1L;
        Superhero superhero = new Superhero();
        superhero.setName("Superman");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> superheroController.updateSuperhero(id, superhero));
        verify(superheroService, never()).getSuperheroById(id);
        verify(superheroService, never()).updateSuperhero(id, superhero);
    }

    @Test
    void testUpdateSuperhero_NullSuperhero_ThrowsIllegalArgumentException() {
        // Arrange
        Long id = 1L;
        Superhero superhero = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> superheroController.updateSuperhero(id, superhero));
        verify(superheroService, never()).getSuperheroById(id);
        verify(superheroService, never()).updateSuperhero(id, superhero);
    }

    @Test
    void testUpdateSuperhero_EmptyName_ThrowsIllegalArgumentException() {
        // Arrange
        Long id = 1L;
        Superhero superhero = new Superhero();
        superhero.setName("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> superheroController.updateSuperhero(id, superhero));
        verify(superheroService, never()).getSuperheroById(id);
        verify(superheroService, never()).updateSuperhero(id, superhero);
    }

    @Test
    void testUpdateSuperhero_NonExistingSuperhero_ReturnsNotFoundResponse() {
        // Arrange
        Long id = 1L;
        Superhero superhero = new Superhero();
        superhero.setName("Superman");

        when(superheroService.getSuperheroById(id)).thenReturn(null);

        // Act
        ResponseEntity<Superhero> response = superheroController.updateSuperhero(id, superhero);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(superheroService, times(1)).getSuperheroById(id);
        verify(superheroService, never()).updateSuperhero(id, superhero);
    }
    @Test
    void testCreateSuperhero_ValidSuperhero_ReturnsCreatedResponse() {
        // Arrange
        Superhero superhero = new Superhero();
        superhero.setName("Superman");
    
        when(superheroService.createSuperhero(superhero)).thenReturn(superhero);
    
        // Act
        ResponseEntity<Superhero> response = superheroController.createSuperhero(superhero);
    
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(superhero, response.getBody());
        verify(superheroService, times(1)).createSuperhero(superhero);
    }
    
    @Test
    void testCreateSuperhero_NullSuperhero_ThrowsIllegalArgumentException() {
        // Arrange
        Superhero superhero = null;
    
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> superheroController.createSuperhero(superhero));
        verify(superheroService, never()).createSuperhero(superhero);
    }
    
    @Test
    void testCreateSuperhero_EmptyName_ThrowsIllegalArgumentException() {
        // Arrange
        Superhero superhero = new Superhero();
        superhero.setName("");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> superheroController.createSuperhero(superhero));
        verify(superheroService, never()).createSuperhero(superhero);
    }

    @Test
    void testDeleteSuperhero_ValidId_ReturnsOkResponse() {
        // Arrange
        Long id = 1L;

        Superhero superhero = new Superhero();
        superhero.setName("Superman");

        when(superheroService.getSuperheroById(id)).thenReturn(new Superhero());
        doNothing().when(superheroService).deleteSuperhero(id);
        // Act
        ResponseEntity<Void> response = superheroController.deleteSuperhero(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(superheroService, times(1)).deleteSuperhero(id);
    }
    
    @Test
    void testDeleteSuperhero_InvalidId_ThrowsIllegalArgumentException() {
        // Arrange
        Long id = -1L;
    
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> superheroController.deleteSuperhero(id));
        verify(superheroService, never()).deleteSuperhero(id);
    }
    
    @Test
    void testGetSuperheroesByName_ValidName_ReturnsListOfSuperheroes() {
    // Arrange
    String name = "Superman";
    List<Superhero> superheroes = new ArrayList<>();
    superheroes.add(new Superhero(1L, "Superman"));
    superheroes.add(new Superhero(2L, "Superwoman"));
    superheroes.add(new Superhero(3L, "Superboy"));

    when(superheroService.getSuperheroesByName(name)).thenReturn(superheroes);

    // Act
    List<Superhero> response = superheroController.getSuperheroesByName(name);

    // Assert
    assertEquals(superheroes, response);
    verify(superheroService, times(1)).getSuperheroesByName(name);
    }

    @Test
        void testGetSuperheroesByName_InvalidName_ReturnsEmptyList() {
    // Arrange
    String name = "InvalidName";

    when(superheroService.getSuperheroesByName(name)).thenReturn(Collections.emptyList());

    // Act
    List<Superhero> response = superheroController.getSuperheroesByName(name);

    // Assert
    assertTrue(response.isEmpty());
    verify(superheroService, times(1)).getSuperheroesByName(name);
    }

    @Test
    void testGetSuperheroesByName_NullName_ThrowsIllegalArgumentException() {
    // Arrange
    String name = null;

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> superheroController.getSuperheroesByName(name));
    verify(superheroService, never()).getSuperheroesByName(name);
    }

}

