package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.models.Color;

import java.util.List;

public class ColorService extends StorageService<Color> {


    public ColorService(CrudRepository<Color> colorRepository) {
        super(colorRepository);
    }

    public void add(Color color){
        checkColor(color);
        this.modelRepository.save(color);
    }

    public void update(Color color){
        checkIfExists(color);
        checkColor(color);
        this.modelRepository.update(color);
    }

    private void checkIfExists(Color color){
        Color searchColor = new Color();
        searchColor.setId(color.getId());
        if(this.modelRepository.query(searchColor).isEmpty()){
            throw new ModelNotFoundException(String.format("Color not found, id = %s", searchColor.getId()));
        }
    }

    private void checkColor(Color color){
       if(!this.modelRepository.query(color).isEmpty()){
           throw new ModelConflictException("Color already exists");
       }
    }
}
