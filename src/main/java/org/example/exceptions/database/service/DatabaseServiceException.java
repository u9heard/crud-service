package org.example.exceptions.database.service;

import org.postgresql.util.PSQLException;

public class DatabaseServiceException extends RuntimeException {

    public DatabaseServiceException(String message) {
        super(message);
    }
}
