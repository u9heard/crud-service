package org.example.exceptions.parsers;

public class PathParseException extends RuntimeException{
    String path;

    public PathParseException(String message) {
        super(message);
    }

    public PathParseException(String message, String path) {
        super(message);
        this.path = path;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", super.getMessage(), path);
    }
}
