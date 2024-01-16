package com.superhero.superheroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is a controller advice that handles exceptions thrown by the SuperheroController.
 * It provides methods to handle specific exceptions and return appropriate ResponseEntity objects.
 */
@ControllerAdvice
public class SuperheroControllerAdvice {

    /**
     * Handles IllegalArgumentException and returns a ResponseEntity with the exception message and HTTP status code 400 (Bad Request).
     *
     * @param e the IllegalArgumentException to be handled
     * @return a ResponseEntity containing the exception message and HTTP status code 400 (Bad Request)
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles NotFoundSuperheroException and returns a ResponseEntity with the exception message and HTTP status code 404 (Not Found).
     *
     * @param ex the NotFoundSuperheroException to be handled
     * @return a ResponseEntity containing the exception message and HTTP status code 404 (Not Found)
     */
    @ExceptionHandler(NotFoundSuperheroException.class)
    public ResponseEntity<String> handleResourceNotFoundException(NotFoundSuperheroException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}