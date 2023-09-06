package org.example.services;

import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;

import java.util.List;

public abstract class StorageService<T> {
    protected CrudRepository<T> modelRepository;

    public StorageService(CrudRepository<T> modelRepository) {
        this.modelRepository = modelRepository;
    }

    public abstract void add(T model);

    public void deleteById(Long id){
        this.modelRepository.delete(id);
    }
    public abstract void update(T model);
    public T getById(Long id){
        T resultModel = this.modelRepository.read(id);
        if(resultModel == null){
            throw new ModelNotFoundException(String.format("Model not found, id = \"%s\"", id.toString()));
        }
        return resultModel;
    }
}
