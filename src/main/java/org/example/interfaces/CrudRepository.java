package org.example.interfaces;

import org.example.models.User;

import java.util.List;

public interface CrudRepository<T> {
    void save(T object);
    void update(T object);
    void delete(QuerySpecification spec);
    List<T> query(QuerySpecification spec);

}
