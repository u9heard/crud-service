package org.example.exceptions.database.access;

public class DatabaseDeleteException extends RuntimeException{
    public DatabaseDeleteException(String message) {
        super(message);
    }
}
