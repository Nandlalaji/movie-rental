package com.nand.assignment.movierental.exception;

/**
 * Custom Exception for MovieNotFound error
 *
 */
public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String exception) {
        super(exception);
    }

}
