package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.Volume;

import java.util.List;

public class VolumeValidator implements ModelValidator<Volume> {
    @Override
    public boolean validate(Volume model) {
        if(model == null){
            return false;
        }
        return validateId(model) && validateVolume(model);
    }

    @Override
    public boolean validateAll(List<Volume> modelList) {
        for (Volume vol : modelList){
            if(!validate(vol)){
                return false;
            }
        }

        return true;
    }

    private boolean validateId(Volume model){
        return model.getId() != null && model.getId() >=0;
    }

    private boolean validateVolume(Volume model){
        return model.getVolume() != null && model.getVolume() > 0;
    }
}
