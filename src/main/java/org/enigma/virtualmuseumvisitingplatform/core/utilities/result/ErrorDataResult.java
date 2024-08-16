package org.enigma.virtualmuseumvisitingplatform.core.utilities.result;

public class ErrorDataResult<T> extends DataResult<T>{
    public ErrorDataResult(T data) {
        super(false, data);
    }

    public ErrorDataResult(T data, String message) {
        super(false, data, message);
    }
}
