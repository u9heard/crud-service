package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Color;
import org.example.models.User;
import java.util.List;
import org.example.repositories.ColorSQLRepository;
import org.example.specifications.color.ColorByIdSpecification;
import org.example.specifications.color.ColorByNameSpecification;

public class ColorService {
    private CrudRepository<Color> colorRepository;

    public ColorService(CrudRepository<Color> colorRepository) {
        this.colorRepository = colorRepository;
    }

    public boolean add(Color color){
        if(checkColor(color)) {
            this.colorRepository.save(color);
            return true;
        }
        return false;
    }

    public void delete(QuerySpecification specification){
        this.colorRepository.delete(specification);
    }

    public boolean update(Color color){
        if(checkColor(color)) {
            this.colorRepository.update(color);
            return true;
        }
        return false;
    }

    public List<Color> get(QuerySpecification specification){
        return this.colorRepository.query(specification);
    }

    private boolean checkColor(Color color){
        return this.colorRepository.query(new ColorByNameSpecification(color.getColor())).isEmpty();
    }
}
