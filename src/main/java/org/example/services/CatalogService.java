package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Catalog;
import org.example.specifications.catalog.CatalogByBrandModelSpecification;

import java.util.List;

public class CatalogService {
    private CrudRepository<Catalog> catalogRepository;

    public CatalogService(CrudRepository<Catalog> catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public boolean add(Catalog catalog){
        if(checkUnique(catalog.getBrand(), catalog.getModel())){
            this.catalogRepository.save(catalog);
            return true;
        }
        return false;
    }

    public void delete(QuerySpecification specification){
        this.catalogRepository.delete(specification);
    }

    public boolean update(Catalog catalog){
        if(checkUnique(catalog.getBrand(), catalog.getModel())){
            this.catalogRepository.update(catalog);
            return true;
        }
        return false;
    }

    public List<Catalog> get(QuerySpecification specification){
        return this.catalogRepository.query(specification);
    }

    private boolean checkUnique(String brand, String model){
        return this.catalogRepository.query(new CatalogByBrandModelSpecification(brand, model)).isEmpty();
    }
}
