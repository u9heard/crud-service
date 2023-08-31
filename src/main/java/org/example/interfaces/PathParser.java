package org.example.interfaces;

public interface PathParser<T> {
    T parsePath(String path, int position);
}
