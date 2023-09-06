package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.models.CarColor;
import org.example.models.Catalog;
import org.example.models.Color;

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
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public void update(CarColor carColor){
        if(checkCatalog(carColor.getIdCar()) && checkColor(carColor.getIdColor())){
            this.modelRepository.update(carColor);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public List<CarColor> getByCarId(Long id){
        CarColor searchModel = new CarColor();
        searchModel.setIdCar(id);

        return this.modelRepository.query(searchModel);
    }

    public void deleteByIdCarIdColor(Long id_car, Long id_color){
        CarColor searchModel = new CarColor();
        searchModel.setIdCar(id_car);
        searchModel.setIdColor(id_color);

        List<CarColor> resiltList = this.modelRepository.query(searchModel);

        if(resiltList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model with id_car = %s, id_color = %s", id_car, id_color));
        }

        deleteById(resiltList.stream().findFirst().get().getId());
    }

    private boolean checkCatalog(Long id){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        return !this.catalogRepository.query(searchCatalog).isEmpty();
    }

    private boolean checkColor(Long id){
        Color searchColor = new Color();
        searchColor.setId(id);
        return !this.colorRepository.query(searchColor).isEmpty();
    }
}
