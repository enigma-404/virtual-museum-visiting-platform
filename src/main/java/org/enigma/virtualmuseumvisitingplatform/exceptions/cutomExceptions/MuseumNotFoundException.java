package org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions;

public class MuseumNotFoundException extends RuntimeException {
    public MuseumNotFoundException(String message) {
        super(message);
    }
}
