package org.example.responses;

public class JsonMessageResponse {
    public static String getJsonMessage(String message){
        return String.format("{\"message\":\"%s\"}", message);
    }
}
