package org.example.interfaces;

import java.util.List;

public interface CrudRepository<T> {
    void save(T object);
    void update(T object);
    void delete(Long id);
    T read(Long id);
    List<T> query(T criteriaModel);

}
