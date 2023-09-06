package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;
import org.example.models.CarVolume;

import java.util.List;

public class CarVolumeValidator implements ModelValidator<CarVolume> {
    @Override
    public boolean validateOnInsert(CarVolume model) {
        return validateIdCar(model) && validateIdVolume(model);
    }

    @Override
    public boolean validateOnUpdate(CarVolume model) {
        return validateIdCar(model) && validateIdVolume(model) && validateId(model);
    }

    private boolean validateId(CarVolume model){
        return model.getId() != null && model.getId() >=1;
    }

    private boolean validateIdCar(CarVolume model){
        return model.getIdCar() != null && model.getIdCar() >=1;
    }

    private boolean validateIdVolume(CarVolume model){
        return model.getIdVolume() != null && model.getIdVolume() >=1;
    }
}
