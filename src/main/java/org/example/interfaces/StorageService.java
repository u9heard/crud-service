package org.example.interfaces;

import org.example.criteria.SearchCriteria;
import org.example.exceptions.database.service.ModelNotFoundException;

import java.util.List;

public abstract class StorageService<T> {
    protected CrudRepository<T> modelRepository;

    public StorageService(CrudRepository<T> modelRepository) {
        this.modelRepository = modelRepository;
    }

    public abstract void add(T model);

    public void deleteById(Long id){
        this.modelRepository.deleteById(id);
    }
    public abstract void update(T model);
    public T getById(Long id){
        List<T> resultList = this.modelRepository.getById(id);
        if(resultList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model not found, id = \"%d\"", id));
        }
        return resultList.get(0);
    }
    public List<T> query(List<SearchCriteria> criteriaList){
        List<T> resultList = this.modelRepository.query(criteriaList);
        if(resultList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model not found, specification = \"%s\"", criteriaList.toString()));
        }
        return resultList;
    }
}
