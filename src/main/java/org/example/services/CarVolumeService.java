package org.example.services;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
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
        throw new ModelConflictException("Unique check failed");
    }

    public void update(CarVolume carVolume){
        if(checkVolume(carVolume.getIdVolume()) && checkCatalog(carVolume.getIdCar())){
            this.modelRepository.update(carVolume);
        }
        throw new ModelConflictException("Unique check failed");
    }

    private boolean checkCatalog(Long id){
        return !this.catalogRepository.query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id))).isEmpty();
    }

    private boolean checkVolume(Long id){
        return !this.volumeRepository.query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id))).isEmpty();
    }
}
