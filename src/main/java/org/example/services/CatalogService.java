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
        checkUniqueOnInsert(catalog.getBrand(), catalog.getModel());
        this.modelRepository.save(catalog);
    }

    public void update(Catalog catalog){
        checkUniqueOnUpdate(catalog.getId(), catalog.getBrand(), catalog.getModel());
        this.modelRepository.update(catalog);

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

    private void checkUniqueOnInsert(String brand, String model){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setBrand(brand);
        searchCatalog.setModel(model);
        if (!this.modelRepository.query(searchCatalog).isEmpty()){
            throw new ModelConflictException("Car already exists");
        }
    }

    private void checkUniqueOnUpdate(Long id, String brand, String model){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        searchCatalog.setBrand(brand);
        searchCatalog.setModel(model);
        if (!this.modelRepository.query(searchCatalog).isEmpty()){
            throw new ModelConflictException("Car already exists");
        }
    }
}
