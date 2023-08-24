package org.example.interfaces;

import java.util.List;

public interface ModelValidator<T> {
    boolean validate(T model);
    boolean validateAll(List<T> modelList);
}
