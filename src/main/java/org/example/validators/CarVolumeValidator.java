package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;
import org.example.models.CarVolume;

import java.util.List;

public class CarVolumeValidator implements ModelValidator<CarVolume> {
    @Override
    public void validateOnInsert(CarVolume model) {
        validateNull(model);
        validateIdCar(model);
        validateIdVolume(model);
    }

    @Override
    public void validateOnUpdate(CarVolume model) {
        validateNull(model);
        validateIdCar(model);
        validateIdVolume(model);
        validateId(model);
    }

    private void validateId(CarVolume model){
        if(model.getId() == null){
            throw new ModelValidationException("Invalid CarVolume id");
        }
    }

    private void validateIdCar(CarVolume model){
        if(model.getIdCar() == null){
            throw new ModelValidationException("Invalid CarColor car id");
        }
    }

    private void validateIdVolume(CarVolume model){
        if(model.getIdVolume() == null){
            throw new ModelValidationException("Invalid CarColor volume id");
        }
    }
}
