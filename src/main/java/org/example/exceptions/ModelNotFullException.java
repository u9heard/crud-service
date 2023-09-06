package org.example.exceptions;

public class ModelNotFullException extends RuntimeException{
    public ModelNotFullException(String message) {
        super(message);
    }
}
