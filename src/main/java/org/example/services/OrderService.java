package org.example.services;

import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.models.*;

import java.util.List;

public class OrderService extends StorageService<Order> {
    CrudRepository<User> userRepository;
    CrudRepository<Catalog> catalogRepository;
    CrudRepository<CarColor> carColorRepository;
    CrudRepository<CarVolume> carVolumeRepository;

    public OrderService(CrudRepository<User> userRepository, CrudRepository<Catalog> catalogRepository, CrudRepository<CarColor> carColorRepository, CrudRepository<CarVolume> carVolumeRepository, CrudRepository<Order> orderRepository) {
        super(orderRepository);
        this.userRepository = userRepository;
        this.catalogRepository = catalogRepository;
        this.carColorRepository = carColorRepository;
        this.carVolumeRepository = carVolumeRepository;
    }

    public void add(Order order){
        checkUser(order.getIdUser());
        checkCatalog(order.getIdCar());
        checkCarColor(order.getIdCar(), order.getIdColor());
        checkCarVolume(order.getIdCar(), order.getIdVolume());
        this.modelRepository.save(order);
    }

    public void update(Order order){
        checkUser(order.getIdUser());
        checkCatalog(order.getIdCar());
        checkCarColor(order.getIdCar(), order.getIdColor());
        checkCarVolume(order.getIdCar(), order.getIdVolume());
        this.modelRepository.update(order);
    }

    private void checkUser(Long id){
        User searchUser = new User();
        searchUser.setId(id);
        if(this.userRepository.query(searchUser).isEmpty()){
            throw new ModelNotFoundException("User not found");
        }
    }

    private void checkCatalog(Long id){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        if(this.catalogRepository.query(searchCatalog).isEmpty()){
            throw new ModelNotFoundException("Car not found");
        }
    }

    private void checkCarColor(Long id_car, Long id_color){
        CarColor searchCarColor = new CarColor();
        searchCarColor.setIdCar(id_car);
        searchCarColor.setIdColor(id_color);
        if(this.carColorRepository.query(searchCarColor).isEmpty()){
            throw new ModelNotFoundException("There is no such color for this car");
        }
    }

    private void checkCarVolume(Long id_car, Long id_volume){
        CarVolume searchCarVolume = new CarVolume();
        searchCarVolume.setIdCar(id_car);
        searchCarVolume.setIdVolume(id_volume);
        if(this.carVolumeRepository.query(searchCarVolume).isEmpty()){
            throw new ModelNotFoundException("There is no such volume for this car");
        }
    }
}
