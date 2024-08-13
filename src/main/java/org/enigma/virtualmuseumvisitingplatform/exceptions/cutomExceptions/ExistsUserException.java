package org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions;

public class ExistsUserException extends RuntimeException {
    public ExistsUserException(String message) {
        super(message);
    }
}
