package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
import org.example.models.Color;
import org.example.models.Volume;
import org.example.repositories.VolumeSQLRepository;
import org.example.specifications.volume.VolumeByIdSpecification;
import org.example.specifications.volume.VolumeByNameSpecification;

import java.util.List;

public class VolumeService extends StorageService<Volume> {

    public VolumeService(CrudRepository<Volume> volumeRepository) {
        super(volumeRepository);
    }

    public void add(Volume volume){
        if(checkVolume(volume)) {
            this.modelRepository.save(volume);
        }
        throw new ModelConflictException("Unique check failed");
    }

    public void update(Volume volume){
        if(checkVolume(volume)) {
            this.modelRepository.update(volume);
        }
        throw new ModelConflictException("Unique check failed");
    }

    private boolean checkVolume(Volume volume){
        return this.modelRepository.query(new VolumeByNameSpecification(volume.getVolume())).isEmpty();
    }
}
