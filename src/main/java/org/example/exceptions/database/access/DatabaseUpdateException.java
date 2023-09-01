package org.example.exceptions.database.access;

public class DatabaseUpdateException extends RuntimeException{
    public DatabaseUpdateException(String message) {
        super(message);
    }
}
