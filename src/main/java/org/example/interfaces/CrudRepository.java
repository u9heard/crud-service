package org.example.interfaces;

import org.example.criteria.SearchCriteria;
import org.example.models.User;

import java.util.List;
import java.util.Objects;

public interface CrudRepository<T> {
    void save(T object);
    void update(T object);
    void deleteById(Long id);
    List<T> getById(Long id);
    List<T> query(List<SearchCriteria> criteriaList);

}
