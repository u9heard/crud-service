package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
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
        checkIfExists(volume);
        checkVolume(volume);
        this.modelRepository.update(volume);
    }

    private void checkVolume(Volume volume){
        Volume searchVolume = new Volume();
        searchVolume.setVolume(volume.getVolume());
        if(!this.modelRepository.query(searchVolume).isEmpty()){
            throw new ModelConflictException("Volume already exists");
        }
    }

    private void checkIfExists(Volume volume){
        Volume searchVolume = new Volume();
        searchVolume.setId(volume.getId());
        if(this.modelRepository.query(searchVolume).isEmpty()){
            throw new ModelNotFoundException(String.format("Volume not found, id = %s", searchVolume.getId()));
        }
    }
}

//TODO SQLException resolver;
