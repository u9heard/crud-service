package org.example.exceptions.database.service;

public class ModelConflictException extends RuntimeException{

    public ModelConflictException(String message) {
        super(message);
    }
}
