package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.interfaces.CrudRepository;
import org.example.models.Color;

import java.util.List;

public class ColorService extends StorageService<Color> {


    public ColorService(CrudRepository<Color> colorRepository) {
        super(colorRepository);
    }

    public void add(Color color){
        if(checkColor(color)) {
            this.modelRepository.save(color);
        }
        else {
            throw new ModelConflictException("Color already exists");
        }
    }

    public void update(Color color){
        if(checkColor(color)) {
            this.modelRepository.update(color);
        }
        else {
            throw new ModelConflictException("Color already exists");
        }
    }

    private boolean checkColor(Color color){
        return this.modelRepository.query(color).isEmpty();
    }
}
