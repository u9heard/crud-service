package org.example.services;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
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
        throw new ModelConflictException("Color already exist");
    }

    public void update(Color color){
        if(checkColor(color)) {
            this.modelRepository.update(color);
        }
        throw new ModelConflictException("Color already exist");
    }

    private boolean checkColor(Color color){
        return this.modelRepository.query(List.of(new SearchCriteria("color", SearchOperator.EQUALS, color.getColor()))).isEmpty();
    }
}
