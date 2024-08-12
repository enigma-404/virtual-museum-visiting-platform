package org.enigma.virtualmuseumvisitingplatform.core.result;

public class SuccessDataResult<T> extends DataResult<T>{
    public SuccessDataResult(T data) {
        super(true, data);
    }

    public SuccessDataResult(T data, String message) {
        super(true, data, message);
    }
}
