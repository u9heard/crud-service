package org.example.exceptions.parsers;

public class RequestParametersException extends RuntimeException{
    public RequestParametersException(String message) {
        super(message);
    }
}
