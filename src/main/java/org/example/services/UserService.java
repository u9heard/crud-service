package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.User;
import org.example.repositories.UserSQLRepository;

import java.util.List;

public class UserService {
    private CrudRepository<User> userRepository;

    public UserService(CrudRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public boolean add(User user){
        this.userRepository.save(user);
        return true;
    }

    public void delete(QuerySpecification spec) {
        this.userRepository.delete(spec);
    }

    public boolean update(User user){
        this.userRepository.update(user);
        return true;
    }

    public List<User> get(QuerySpecification specification){
        return this.userRepository.query(specification);
    }
}
