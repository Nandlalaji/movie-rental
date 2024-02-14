package com.nand.assignment.movierental.exception;

/**
 * Custom Exception for UnKnownCategory error
 *
 */
public class UnKnownCategoryException extends RuntimeException {
    public UnKnownCategoryException(String exception) {
        super(exception);
    }

}
