package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.Color;

import java.util.List;

public class ColorValidator implements ModelValidator<Color> {
    @Override
    public boolean validateOnInsert(Color model) {
        return validateId(model) && validateColor(model);
    }

    @Override
    public boolean validateOnUpdate(Color model) {
        return validateColor(model);
    }

    private boolean validateId(Color model){
        return model.getId() != null && model.getId() >=0;
    }

    private boolean validateColor(Color model){
        return model.getColor() != null && !model.getColor().isEmpty();
    }
}
