package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;

import java.util.List;

public class CarColorValidator implements ModelValidator<CarColor> {
    @Override
    public boolean validateOnInsert(CarColor model) {
        return validateIdCar(model) && validateIdColor(model);
    }

    @Override
    public boolean validateOnUpdate(CarColor model) {
        return validateId(model) && validateIdCar(model) && validateIdColor(model);
    }

    private boolean validateId(CarColor model){
        return model.getId() != null && model.getId() >= 1;
    }

    private boolean validateIdCar(CarColor model){
        return model.getIdCar() != null && model.getIdCar() >=1;
    }

    private boolean validateIdColor(CarColor model){
        return model.getIdColor() != null && model.getIdColor() >=1;
    }
}
