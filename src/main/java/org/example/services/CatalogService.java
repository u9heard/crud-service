package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
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
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public void update(Catalog catalog){
        if(checkUniqueOnUpdate(catalog.getId(), catalog.getBrand(), catalog.getModel())){
            this.modelRepository.update(catalog);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public List<Catalog> getByBrand(String brand){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setBrand(brand);
        List<Catalog> result = this.modelRepository.query(searchCatalog);
        if(result.isEmpty()){
            throw new ModelNotFoundException("Brand not found");
        }
        return result;
    }

    private boolean checkUniqueOnInsert(String brand, String model){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setBrand(brand);
        searchCatalog.setModel(model);
        return this.modelRepository.query(searchCatalog).isEmpty();
    }

    private boolean checkUniqueOnUpdate(Long id, String brand, String model){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        searchCatalog.setBrand(brand);
        searchCatalog.setModel(model);
        if(this.modelRepository.query(searchCatalog).isEmpty()) {
            return checkUniqueOnInsert(brand, model);
        }
        return true;
    }
}
