package org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions;

public class ArtifactAlreadyExists extends RuntimeException {
    public ArtifactAlreadyExists(String message) {
        super(message);
    }
}
