package org.example.responses;

public enum ErrorJsonResponse {
    MODEL_NOT_FULL("Model not full"),
    CHECK_INPUT_DATA("Check input data"),
    ENTITY_MISSING("One or more entities are missing"),
    UNIQUE_CHECK_ERROR("The model already exists"),
    NOT_FOUND("Not found");
    private String message;
    ErrorJsonResponse(String message) {
        this.message = message;
    }

    public String getJsonMessage() {
        return String.format("{\"message\":\"%s\"}", message);
    }
}
