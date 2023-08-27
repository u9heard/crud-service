package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.Order;

import java.util.List;

public class OrderValidator implements ModelValidator<Order> {
    @Override
    public boolean validate(Order model) {
        return validateId(model) &&
                validateIdUser(model) &&
                validateIdCatalog(model) &&
                validateIdColor(model) &&
                validateIdVolume(model) &&
                validateDate(model);
    }

    @Override
    public boolean validateAll(List<Order> modelList) {
        for(Order order : modelList){
            if(!validate(order))
                return false;
        }

        return true;
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