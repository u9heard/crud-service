package org.example.exceptions.database.access;

public class DatabaseReadException extends RuntimeException{
    public DatabaseReadException(String message) {
        super(message);
    }
}
