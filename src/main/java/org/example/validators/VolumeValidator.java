package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.Volume;

import java.util.List;

public class VolumeValidator implements ModelValidator<Volume> {
    @Override
    public boolean validateOnInsert(Volume model) {
        if(model == null){
            return false;
        }
        return validateVolume(model);
    }

    @Override
    public boolean validateOnUpdate(Volume model) {
        return validateId(model) && validateOnInsert(model);
    }

    private boolean validateId(Volume model){
        return model.getId() != null && model.getId() >=0;
    }

    private boolean validateVolume(Volume model){
        return model.getVolume() != null && model.getVolume() > 0;
    }
}
