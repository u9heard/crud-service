package org.example.exceptions;

public class EmptyJsonException extends RuntimeException{
    public EmptyJsonException(String message) {
        super(message);
    }
}
