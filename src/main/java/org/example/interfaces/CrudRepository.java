package org.example.interfaces;

import org.example.criteria.SearchCriteria;
import org.example.models.User;

import java.util.List;
import java.util.Objects;
import java.util.ServiceConfigurationError;

public interface CrudRepository<T> {
    void save(T object);
    void update(T object);
    void delete(Long id);
    T read(Long id);
    List<T> query(T criteriaModel);

}
