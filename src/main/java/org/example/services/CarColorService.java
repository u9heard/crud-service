package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
import org.example.models.CarColor;
import org.example.models.Catalog;
import org.example.models.Color;
import org.example.specifications.catalog.CatalogByIdSpecification;
import org.example.specifications.color.ColorByIdSpecification;

import java.util.List;

public class CarColorService extends StorageService<CarColor> {
    private CrudRepository<Catalog> catalogRepository;
    private CrudRepository<Color> colorRepository;

    public CarColorService(CrudRepository<Catalog> catalogRepository, CrudRepository<Color> colorRepository, CrudRepository<CarColor> carColorRepository) {
        super(carColorRepository);
        this.catalogRepository = catalogRepository;
        this.colorRepository = colorRepository;
    }

    public void add(CarColor carColor){
        if(checkCatalog(carColor.getIdCar()) && checkColor(carColor.getIdColor())){
            this.modelRepository.save(carColor);
        }
        throw new ModelConflictException("Unique check failed");
    }

    public void update(CarColor carColor){
        if(checkCatalog(carColor.getIdCar()) && checkColor(carColor.getIdColor())){
            this.modelRepository.update(carColor);
        }
        throw new ModelConflictException("Unique check failed");
    }


    private boolean checkCatalog(Long id){
        return !this.catalogRepository.query(new CatalogByIdSpecification(id)).isEmpty();
    }

    private boolean checkColor(Long id){
        return !this.colorRepository.query(new ColorByIdSpecification(id)).isEmpty();
    }
}
