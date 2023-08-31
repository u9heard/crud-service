package org.example.interfaces;

import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.specifications.DefaultByIdSpecification;
import org.example.specifications.order.OrderByIdSpecification;

import java.util.List;

public abstract class StorageService<T> {
    protected CrudRepository<T> modelRepository;

    public StorageService(CrudRepository<T> modelRepository) {
        this.modelRepository = modelRepository;
    }

    public abstract void add(T model);
    public void deleteBySpecification(QuerySpecification specification){
        this.modelRepository.delete(specification);
    }
    public void deleteById(Long id){
        this.modelRepository.delete(new DefaultByIdSpecification(id));
    }
    public abstract void update(T model);
    public T getById(Long id){
        List<T> resultList = this.modelRepository.query(new DefaultByIdSpecification(id));
        if(resultList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model not found, id = \"%d\"", id));
        }
        return resultList.get(0);
    }
    public List<T> query(QuerySpecification specification){
        List<T> resultList = this.modelRepository.query(specification);
        if(resultList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model not found, specification = \"%s\"", specification.toSQLClauses()));
        }
        return resultList;
    }
}
