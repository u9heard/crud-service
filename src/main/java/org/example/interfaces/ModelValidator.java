package org.example.interfaces;

import org.example.exceptions.ModelValidationException;

import java.util.List;

public interface ModelValidator<T> {
    void validateOnInsert(T model);
    void validateOnUpdate(T model);

    default void validateNull(T model){
        if(model == null) {
            throw new ModelValidationException("Model is empty");
        }
    }
}
