package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;
import org.example.models.CarVolume;
import org.example.models.Catalog;
import org.example.models.Volume;
import org.example.specifications.catalog.CatalogByIdSpecification;
import org.example.specifications.volume.VolumeByIdSpecification;

import java.util.List;

public class CarVolumeService {
    private CrudRepository<Catalog> catalogRepository;
    private CrudRepository<Volume> volumeRepository;
    private CrudRepository<CarVolume> carVolumeRepository;

    public CarVolumeService(CrudRepository<Catalog> catalogRepository, CrudRepository<Volume> volumeRepository, CrudRepository<CarVolume> carVolumeRepository) {
        this.catalogRepository = catalogRepository;
        this.volumeRepository = volumeRepository;
        this.carVolumeRepository = carVolumeRepository;
    }

    public boolean add(CarVolume carVolume){
        if(checkVolume(carVolume.getIdVolume()) && checkCatalog(carVolume.getIdCar())){
            this.carVolumeRepository.save(carVolume);
            return true;
        }
        return false;
    }

    public void delete(QuerySpecification specification){
        this.carVolumeRepository.delete(specification);
    }

    public boolean update(CarVolume carVolume){
        if(checkVolume(carVolume.getIdVolume()) && checkCatalog(carVolume.getIdCar())){
            this.carVolumeRepository.update(carVolume);
            return true;
        }
        return false;
    }

    public List<CarVolume> get(QuerySpecification specification){
        return this.carVolumeRepository.query(specification);
    }

    private boolean checkCatalog(Long id){
        return !this.catalogRepository.query(new CatalogByIdSpecification(id)).isEmpty();
    }

    private boolean checkVolume(Long id){
        return !this.volumeRepository.query(new VolumeByIdSpecification(id)).isEmpty();
    }
}
