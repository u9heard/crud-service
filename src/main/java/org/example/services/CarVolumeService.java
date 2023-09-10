package org.example.services;

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
        checkVolume(carVolume.getIdVolume());
        checkCatalog(carVolume.getIdCar());
        this.modelRepository.save(carVolume);

    }

    public void update(CarVolume carVolume){
        checkIfExists(carVolume);
        checkVolume(carVolume.getIdVolume());
        checkCatalog(carVolume.getIdCar());
        this.modelRepository.update(carVolume);
    }

    public List<CarVolume> getByCarId(Long id){
        CarVolume searchModel = new CarVolume();
        searchModel.setIdCar(id);

        return modelRepository.query(searchModel);
    }

    public void deleteByIdCarIdVolume(Long id_car, Long id_volume){
        CarVolume searchModel = new CarVolume();
        searchModel.setIdCar(id_car);
        searchModel.setIdVolume(id_volume);

        List<CarVolume> resiltList = this.modelRepository.query(searchModel);

        if(resiltList.isEmpty()){
            throw new ModelNotFoundException(String.format("Model with id_car = %s, id_volume = %s", id_car, id_volume));
        }

        deleteById(resiltList.stream().findFirst().get().getId());
    }

    private void checkCatalog(Long id){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        if(this.catalogRepository.query(searchCatalog).isEmpty()){
            throw new ModelNotFoundException("Car not found");
        }
    }

    private void checkVolume(Long id){
        Volume searchVolume = new Volume();
        searchVolume.setId(id);
        if(this.volumeRepository.query(searchVolume).isEmpty()){
            throw new ModelNotFoundException("Volume not found");
        }
    }

    private void checkIfExists(CarVolume color){
        CarVolume searchVolume = new CarVolume();
        searchVolume.setId(color.getId());
        if(this.modelRepository.query(searchVolume).isEmpty()){
            throw new ModelNotFoundException(String.format("Volume for car not found, id = %s", searchVolume.getId()));
        }
    }
}
