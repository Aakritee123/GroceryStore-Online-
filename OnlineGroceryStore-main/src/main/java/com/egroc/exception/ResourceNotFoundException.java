package com.egroc.exception;

/**
 * Custom exception to handle resource not found scenarios.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message); // Pass the error message to the RuntimeException constructor
    }
}
