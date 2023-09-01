package org.example.exceptions.database.access;

public class DatabaseSaveException extends RuntimeException{
    public DatabaseSaveException(String message) {
        super(message);
    }
}
