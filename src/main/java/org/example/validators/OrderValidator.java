package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.Order;

import java.util.List;

public class OrderValidator implements ModelValidator<Order> {
    @Override
    public void validateOnInsert(Order model) {
        validateNull(model);
        validateIdUser(model);
        validateIdCatalog(model);
        validateIdColor(model);
        validateIdVolume(model);
        validateDate(model);
    }

    @Override
    public void validateOnUpdate(Order model) {
        validateNull(model);
        validateId(model);
        validateOnInsert(model);
    }

    private void validateId(Order model){
        if(model.getId() == null){
            throw new ModelValidationException("Invalid order id");
        }
    }

    private void validateIdUser(Order model){
        if(model.getIdUser() == null){
            throw new ModelValidationException("Invalid order user id");
        }
    }

    private void validateIdCatalog(Order model){
        if(model.getIdCar() == null){
            throw new ModelValidationException("Invalid order catalog id");
        }
    }

    private void validateIdColor(Order model){
        if(model.getIdColor() == null){
            throw new ModelValidationException("Invalid order color id");
        }
    }

    private void validateIdVolume(Order model){
        if(model.getIdVolume() == null){
            throw new ModelValidationException("Invalid order volume id");
        }
    }

    private void validateDate(Order model){
        if(model.getDateBuy() == null){
            throw new ModelValidationException("Invalid order date value");
        }
    }
}
