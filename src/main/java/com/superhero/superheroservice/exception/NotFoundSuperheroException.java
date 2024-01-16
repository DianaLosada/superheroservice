/**
 * Exception thrown when a superhero is not found.
 */
package com.superhero.superheroservice.exception;

public class NotFoundSuperheroException extends RuntimeException{

    public NotFoundSuperheroException(String message) {
        super(message);
    }

}
