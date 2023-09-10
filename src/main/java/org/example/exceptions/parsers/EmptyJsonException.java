package org.example.exceptions.parsers;

public class EmptyJsonException extends RuntimeException{
    public EmptyJsonException(String message) {
        super(message);
    }
}
