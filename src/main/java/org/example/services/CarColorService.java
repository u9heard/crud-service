package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;
import org.example.models.Catalog;
import org.example.models.Color;
import org.example.specifications.catalog.CatalogByIdSpecification;
import org.example.specifications.color.ColorByIdSpecification;

import java.util.List;

public class CarColorService {
    private CrudRepository<Catalog> catalogRepository;
    private CrudRepository<Color> colorRepository;
    private CrudRepository<CarColor> carColorRepository;

    public CarColorService(CrudRepository<Catalog> catalogRepository, CrudRepository<Color> colorRepository, CrudRepository<CarColor> carColorRepository) {
        this.catalogRepository = catalogRepository;
        this.colorRepository = colorRepository;
        this.carColorRepository = carColorRepository;
    }

    public boolean add(CarColor carColor){
        if(checkCatalog(carColor.getIdCar()) && checkColor(carColor.getIdColor())){
            this.carColorRepository.save(carColor);
            return true;
        }
        return false;
    }

    public void delete(QuerySpecification spec){
        this.carColorRepository.delete(spec);
    }

    public boolean update(CarColor carColor){
        if(checkCatalog(carColor.getIdCar()) && checkColor(carColor.getIdColor())){
            this.carColorRepository.update(carColor);
            return true;
        }
        return false;
    }

    public List<CarColor> get(QuerySpecification spec){
        return this.carColorRepository.query(spec);
    }

    private boolean checkCatalog(Long id){
        return !this.catalogRepository.query(new CatalogByIdSpecification(id)).isEmpty();
    }

    private boolean checkColor(Long id){
        return !this.colorRepository.query(new ColorByIdSpecification(id)).isEmpty();
    }
}
