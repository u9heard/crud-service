package org.example.exceptions.parsers;

public class JsonParseException extends RuntimeException{
    public JsonParseException(String message) {
        super(message);
    }
}
