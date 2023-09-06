package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.Order;

import java.util.List;

public class OrderValidator implements ModelValidator<Order> {
    @Override
    public boolean validateOnInsert(Order model) {
        return validateIdUser(model) &&
                validateIdCatalog(model) &&
                validateIdColor(model) &&
                validateIdVolume(model) &&
                validateDate(model);
    }

    @Override
    public boolean validateOnUpdate(Order model) {
        return validateId(model) && validateOnInsert(model);
    }

    private boolean validateId(Order model){
        return model.getId() != null && model.getId() >= 0;
    }

    private boolean validateIdUser(Order model){
        return model.getIdUser() != null && model.getIdUser() >= 1;
    }

    private boolean validateIdCatalog(Order model){
        return model.getIdCar() != null && model.getIdCar() >= 1;
    }

    private boolean validateIdColor(Order model){
        return model.getIdColor() != null && model.getIdColor() >= 1;
    }

    private boolean validateIdVolume(Order model){
        return model.getIdVolume() != null && model.getIdVolume() >= 1;
    }

    private boolean validateDate(Order model){
        return model.getDateBuy() != null;
    }
}
