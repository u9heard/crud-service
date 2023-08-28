package org.example.interfaces;

public interface ModelParser<T> {
    T toModel(String textModel);
    String toJSON(Object object);
}
