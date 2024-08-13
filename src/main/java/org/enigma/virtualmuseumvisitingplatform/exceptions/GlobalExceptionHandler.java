package org.enigma.virtualmuseumvisitingplatform.exceptions;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.ErrorDataResult;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ExistsUserException;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.RoleNotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<DataResult<?>> handlingMethodArgumentValid(MethodArgumentNotValidException exceptions){
        Map<String, String> validationErrors = new HashMap<>();
        for(FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(new ErrorDataResult<>(validationErrors, "validation error"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFountException.class)
    public ResponseEntity<String> handleRoleNotFountException() {
        return new ResponseEntity<>("role not found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistsUserException.class)
    public ResponseEntity<String> handleExistsUserException() {
        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
    }


}
