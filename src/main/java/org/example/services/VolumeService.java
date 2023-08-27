package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Color;
import org.example.models.Volume;
import org.example.repositories.VolumeSQLRepository;
import org.example.specifications.volume.VolumeByIdSpecification;
import org.example.specifications.volume.VolumeByNameSpecification;

import java.util.List;

public class VolumeService {
    private CrudRepository<Volume> volumeRepository;

    public VolumeService(VolumeSQLRepository volumeRepository) {
        this.volumeRepository = volumeRepository;
    }

    public boolean add(Volume volume){
        if(checkVolume(volume)) {
            this.volumeRepository.save(volume);
            return true;
        }
        return false;
    }

    public void delete(QuerySpecification specification){
        this.volumeRepository.delete(specification);
    }

    public boolean update(Volume volume){
        if(checkVolume(volume)) {
            this.volumeRepository.update(volume);
            return true;
        }
        return false;
    }

    public List<Volume> get(QuerySpecification specification){
        return this.volumeRepository.query(specification);
    }

    private boolean checkVolume(Volume volume){
        return this.volumeRepository.query(new VolumeByNameSpecification(volume.getVolume())).isEmpty();
    }
}
