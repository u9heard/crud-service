package org.example.validators;

import org.example.exceptions.ModelValidationException;
import org.example.interfaces.ModelValidator;
import org.example.models.User;

import java.util.List;

public class UserValidator implements ModelValidator<User> {

    public UserValidator() {
    }

    @Override
    public void validateOnInsert(User user) {
        validateNull(user);
        validateName(user);
        validateSurname(user);
        validateFatherName(user);
        validateDOB(user);
        validateSex(user);
    }

    @Override
    public void validateOnUpdate(User model) {
        validateOnInsert(model);
        validateId(model);
    }

    private void validateId(User user){
        if(user.getId() == null){
            throw new ModelValidationException("Invalid user id");
        }
    }

    private void validateName(User user){
        if(user.getName() == null){
            throw new ModelValidationException("Invalid name");
        }
    }

    private void validateSurname(User user){
        if(user.getSurname() == null){
            throw new ModelValidationException("Invalid surname");
        }
    }

    private void validateFatherName(User user){
        if(user.getFather_name() == null){
            throw new ModelValidationException("Invalid father name");
        }
    }

    private void validateDOB(User user){
        if(user.getDob() == null){
            throw new ModelValidationException("Invalid date of birth value");
        }
    }

    private void validateSex(User user){
        if(user.getSex() == null || user.getSex().isEmpty() || !(user.getSex().equals("Male") || user.getSex().equals("Female"))){
            throw new ModelValidationException("Invalid gender");
        }
    }
}
