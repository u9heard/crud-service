package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;
import org.example.models.CarVolume;

import java.util.List;

public class CarVolumeValidator implements ModelValidator<CarVolume> {
    @Override
    public boolean validate(CarVolume model) {
        return validateIdCar(model) && validateIdVolume(model);
    }

    @Override
    public boolean validateAll(List<CarVolume> modelList) {
        for(CarVolume carVolume : modelList){
            if(!validate(carVolume))
                return false;
        }

        return true;
    }

    private boolean validateIdCar(CarVolume model){
        return model.getIdCar() != null && model.getIdCar() >=1;
    }

    private boolean validateIdVolume(CarVolume model){
        return model.getIdVolume() != null && model.getIdVolume() >=1;
    }
}
