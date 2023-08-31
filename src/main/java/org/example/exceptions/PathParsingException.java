package org.example.exceptions;

public class PathParsingException extends RuntimeException{
    String path;

    public PathParsingException(String message) {
        super(message);
    }

    public PathParsingException(String message, String path) {
        super(message);
        this.path = path;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", super.getMessage(), path);
    }
}
