package org.example.interfaces;

import org.example.models.User;

import java.util.List;

public interface CrudRepository<T, K> {
    T getById(K id);
    void save(T object);
    void update(T object);
    void delete(K id);
    List<T> query(QuerySpecification spec);

}
