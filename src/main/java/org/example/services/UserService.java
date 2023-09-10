package org.example.services;

import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.models.User;

public class UserService extends StorageService<User> {


    public UserService(CrudRepository<User> userRepository) {
        super(userRepository);
    }

    public void add(User user){
        this.modelRepository.save(user);
    }

    public void update(User user){
        checkIfExists(user);
        this.modelRepository.update(user);
    }

    void checkIfExists(User model){
        if(this.modelRepository.query(model).isEmpty()){
            throw new ModelNotFoundException(String.format("User not found, id = %s", model.getId()));
        }
    }
}
