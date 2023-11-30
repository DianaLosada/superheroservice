package com.superhero.superheroservice.timed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a method for timing purposes.
 * When applied to a method, it allows the system to measure the execution time of that method.
 * The annotation is retained at runtime and can be applied to methods only.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Timed {
}