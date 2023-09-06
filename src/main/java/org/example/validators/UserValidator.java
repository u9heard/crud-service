package org.example.validators;

import org.example.interfaces.ModelValidator;
import org.example.models.User;

import java.util.List;

public class UserValidator implements ModelValidator<User> {



    public UserValidator() {

    }

    @Override
    public boolean validateOnInsert(User user) {
        if(user == null){
            return false;
        }
        return validateName(user) &&
                validateSurname(user) &&
                validateFatherName(user) &&
                validateDOB(user) &&
                validateSex(user);
    }

    @Override
    public boolean validateOnUpdate(User model) {
        return validateId(model) && validateOnInsert(model);
    }

    private boolean validateId(User user){
        return user.getId() != null && user.getId() >= 0;
    }

    private boolean validateName(User user){
        return user.getName() != null && !user.getName().isEmpty();
    }

    private boolean validateSurname(User user){
        return user.getSurname() != null && !user.getSurname().isEmpty();
    }

    private boolean validateFatherName(User user){
        return user.getFather_name() != null && !user.getFather_name().isEmpty();
    }

    private boolean validateDOB(User user){
        return user.getDob() != null;
    }

    private boolean validateSex(User user){
        return user.getSex() != null && !user.getSex().isEmpty() && (user.getSex().equals("Male") || user.getSex().equals("Female"));
    }
}
