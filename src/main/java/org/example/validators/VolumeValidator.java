package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.Volume;

import java.util.List;

public class VolumeValidator implements ModelValidator<Volume> {
    @Override
    public void validateOnInsert(Volume model) {
        validateNull(model);
        validateVolume(model);
    }

    @Override
    public void validateOnUpdate(Volume model) {
        validateNull(model);
        validateId(model);
        validateOnInsert(model);
    }

    private void validateId(Volume model){
        if(model.getId() == null){
            throw new ModelValidationException("Invalid volume id");
        }
    }

    private void validateVolume(Volume model){
        if(model.getVolume() == null){
            throw new ModelValidationException("Invalid volume name");
        }
    }
}
