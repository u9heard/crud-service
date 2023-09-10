package org.example.exceptions;

public class ModelValidationException extends RuntimeException{
    public ModelValidationException(String message) {
        super(message);
    }
}
