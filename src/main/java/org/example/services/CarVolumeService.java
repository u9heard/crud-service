package org.example.services;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.models.CarColor;
import org.example.models.CarVolume;
import org.example.models.Catalog;
import org.example.models.Volume;

import java.util.List;

public class CarVolumeService extends StorageService<CarVolume> {
    private final CrudRepository<Catalog> catalogRepository;
    private final CrudRepository<Volume> volumeRepository;

    public CarVolumeService(CrudRepository<Catalog> catalogRepository, CrudRepository<Volume> volumeRepository, CrudRepository<CarVolume> carVolumeRepository) {
        super(carVolumeRepository);
        this.catalogRepository = catalogRepository;
        this.volumeRepository = volumeRepository;
    }

    public void add(CarVolume carVolume){
        if(checkVolume(carVolume.getIdVolume()) && checkCatalog(carVolume.getIdCar())){
            this.modelRepository.save(carVolume);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public void update(CarVolume carVolume){
        if(checkVolume(carVolume.getIdVolume()) && checkCatalog(carVolume.getIdCar())){
            this.modelRepository.update(carVolume);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public List<CarVolume> getByCarId(Long id){
        CarVolume searchModel = new CarVolume();
        searchModel.setIdCar(id);

        return query(searchModel);
    }

    public void deleteByIdCarIdVolume(Long id_car, Long id_volume){
        CarVolume searchModel = new CarVolume();
        searchModel.setIdCar(id_car);
        searchModel.setIdVolume(id_volume);

        List<CarVolume> resiltList = query(searchModel);

        if(resiltList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model with id_car = %s, id_volume = %s", id_car, id_volume));
        }

        deleteById(resiltList.stream().findFirst().get().getId());
    }

    private boolean checkCatalog(Long id){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        return !this.catalogRepository.query(searchCatalog).isEmpty();
    }

    private boolean checkVolume(Long id){
        Volume searchVolume = new Volume();
        searchVolume.setId(id);
        return !this.volumeRepository.query(searchVolume).isEmpty();
    }
}
