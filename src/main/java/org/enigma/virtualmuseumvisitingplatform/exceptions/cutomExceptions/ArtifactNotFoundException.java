package org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions;

public class ArtifactNotFoundException extends RuntimeException {
    public ArtifactNotFoundException(String message) {
        super(message);
    }
}
