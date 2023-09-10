package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.Color;

import java.util.List;

public class ColorValidator implements ModelValidator<Color> {
    @Override
    public void validateOnInsert(Color model) {
        validateNull(model);
        validateColor(model);
    }

    @Override
    public void validateOnUpdate(Color model) {
        validateNull(model);
        validateId(model);
        validateColor(model);
    }

    private void validateId(Color model){
        if(model.getId() == null){
            throw new ModelValidationException("Invalid color id");
        }
    }

    private void validateColor(Color model){
        if(model.getColor() == null){
            throw new ModelValidationException("Invalid color name");
        }
    }
}
