package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;

import java.util.List;

public class CarColorValidator implements ModelValidator<CarColor> {
    @Override
    public boolean validate(CarColor model) {
        return validateIdCar(model) && validateIdColor(model);
    }

    @Override
    public boolean validateAll(List<CarColor> modelList) {
        for(CarColor carColor : modelList){
            if(!validate(carColor))
                return false;
        }

        return true;
    }

    private boolean validateIdCar(CarColor model){
        return model.getIdCar() != null && model.getIdCar() >=1;
    }

    private boolean validateIdColor(CarColor model){
        return model.getIdColor() != null && model.getIdColor() >=1;
    }
}
