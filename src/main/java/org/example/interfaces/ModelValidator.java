package org.example.interfaces;

import java.util.List;

public interface ModelValidator<T> {
    boolean validateOnInsert(T model);
    boolean validateOnUpdate(T model);
}
