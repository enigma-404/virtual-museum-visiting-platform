package org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
