package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;

import java.util.List;

public class CarColorValidator implements ModelValidator<CarColor> {
    @Override
    public void validateOnInsert(CarColor model) {
        validateNull(model);
        validateIdCar(model);
        validateIdColor(model);
    }

    @Override
    public void validateOnUpdate(CarColor model) {
        validateNull(model);
        validateId(model);
        validateIdCar(model);
        validateIdColor(model);
    }

    private void validateId(CarColor model){
        if(model.getId() == null){
            throw new ModelValidationException("Invalid CarColor id");
        }
    }

    private void validateIdCar(CarColor model){
        if(model.getIdCar() == null){
            throw new ModelValidationException("Invalid CarColor car id");
        }
    }

    private void validateIdColor(CarColor model){
        if(model.getIdColor() == null){
            throw new ModelValidationException("Invalid CarColor color id");
        }
    }
}
