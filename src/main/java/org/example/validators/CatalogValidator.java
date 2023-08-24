package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.Catalog;

import java.util.List;

public class CatalogValidator implements ModelValidator<Catalog> {

    public CatalogValidator() {

    }
    @Override
    public boolean validate(Catalog model) {
        if(model == null){
            return false;
        }
        return validateId(model) &&
                validateBrand(model) &&
                validateModel(model) &&
                validateDate(model);
    }

    @Override
    public boolean validateAll(List<Catalog> modelList) {
        for(Catalog catalog : modelList){
            if(!validate(catalog)){
                return false;
            }
        }
        return true;
    }

    private boolean validateId(Catalog model){
        return model.getId() != null && model.getId() >= 0;
    }

    private boolean validateBrand(Catalog model){
        return model.getBrand() != null && !model.getBrand().isEmpty();
    }

    private boolean validateModel(Catalog model){
        return model.getModel() != null && !model.getModel().isEmpty();
    }

    private boolean validateDate(Catalog model){
        return model.getRelease_date() != null;
    }
}
