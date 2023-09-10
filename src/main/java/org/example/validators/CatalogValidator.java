package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.Catalog;

import java.util.List;

public class CatalogValidator implements ModelValidator<Catalog> {

    public CatalogValidator() {

    }
    @Override
    public void validateOnInsert(Catalog model) {
        validateNull(model);
        validateBrand(model);
        validateModel(model);
        validateDate(model);
    }

    @Override
    public void validateOnUpdate(Catalog model) {
        validateNull(model);
        validateId(model);
        validateBrand(model);
        validateModel(model);
        validateDate(model);
    }

    private void validateId(Catalog model){
        if(model.getId() == null){
            throw new ModelValidationException("Invalid catalog id");
        }
    }

    private void validateBrand(Catalog model){
        if(model.getBrand() == null){
            throw new ModelValidationException("Invalid catalog brand name");
        }
    }

    private void validateModel(Catalog model){
        if(model.getModel() == null){
            throw new ModelValidationException("Invalid catalog model name");
        }
    }

    private void validateDate(Catalog model){
        if(model.getRelease_date() == null){
            throw new ModelValidationException("Invalid catalog release date value");
        }
    }
}
