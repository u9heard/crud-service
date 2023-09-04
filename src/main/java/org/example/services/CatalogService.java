package org.example.services;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
import org.example.models.Catalog;

import java.util.List;

public class CatalogService extends StorageService<Catalog> {

    public CatalogService(CrudRepository<Catalog> catalogRepository) {
        super(catalogRepository);
    }

    public void add(Catalog catalog){
        if(checkUniqueOnInsert(catalog.getBrand(), catalog.getModel())){
            this.modelRepository.save(catalog);
        }
        throw new ModelConflictException("Unique check failed");
    }

    public void update(Catalog catalog){
        if(checkUniqueOnUpdate(catalog.getId(), catalog.getBrand(), catalog.getModel())){
            this.modelRepository.update(catalog);
        }
        throw new ModelConflictException("Unique check failed");
    }

    private boolean checkUniqueOnInsert(String brand, String model){

        return this.modelRepository.query(List.of(
                new SearchCriteria("brand", SearchOperator.EQUALS, brand),
                new SearchCriteria("model", SearchOperator.EQUALS, model))).isEmpty();
    }

    private boolean checkUniqueOnUpdate(Long id, String brand, String model){
        if(this.modelRepository.query(List.of(
                new SearchCriteria("id", SearchOperator.EQUALS, id.toString()),
                new SearchCriteria("brand", SearchOperator.EQUALS, brand),
                new SearchCriteria("model", SearchOperator.EQUALS, model))).isEmpty()) {
            return checkUniqueOnInsert(brand, model);
        }
        return true;
    }
}
