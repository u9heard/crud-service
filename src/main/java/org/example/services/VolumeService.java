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
        checkVolume(volume);
        this.modelRepository.save(volume);
    }

    public void update(Volume volume){
        checkVolume(volume);
        this.modelRepository.update(volume);
    }

    private void checkVolume(Volume volume){
        if(!this.modelRepository.query(volume).isEmpty()){
            throw new ModelConflictException("Volume already exists");
        }
    }
}
