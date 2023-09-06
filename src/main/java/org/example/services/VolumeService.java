package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.interfaces.CrudRepository;
import org.example.models.Volume;

import java.util.List;

public class VolumeService extends StorageService<Volume> {

    public VolumeService(CrudRepository<Volume> volumeRepository) {
        super(volumeRepository);
    }

    public void add(Volume volume){
        if(checkVolume(volume)) {
            this.modelRepository.save(volume);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public void update(Volume volume){
        if(checkVolume(volume)) {
            this.modelRepository.update(volume);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    private boolean checkVolume(Volume volume){
        return this.modelRepository.query(volume).isEmpty();
    }
}
