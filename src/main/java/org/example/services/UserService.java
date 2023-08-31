package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
import org.example.models.User;
import org.example.repositories.UserSQLRepository;
import org.example.specifications.user.UserByIdSpecification;

import java.util.List;

public class UserService extends StorageService<User> {


    public UserService(CrudRepository<User> userRepository) {
        super(userRepository);
    }

    public void add(User user){
        this.modelRepository.save(user);
    }

    public void update(User user){
        this.modelRepository.update(user);
    }
}
