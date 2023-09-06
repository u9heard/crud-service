package org.example.services;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.exceptions.database.service.ModelConflictException;
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
        if(checkUser(order.getIdUser()) && checkCatalog(order.getIdCar()) &&
           checkCarColor(order.getIdCar(), order.getIdColor()) && checkCarVolume(order.getIdCar(), order.getIdVolume())){
            this.modelRepository.save(order);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    public void update(Order order){
        if(checkUser(order.getIdUser()) && checkCatalog(order.getIdCar()) &&
                checkCarColor(order.getIdCar(), order.getIdColor()) && checkCarVolume(order.getIdCar(), order.getIdVolume())){
            this.modelRepository.save(order);
        }
        else {
            throw new ModelConflictException("Unique check failed");
        }
    }

    private boolean checkUser(Long id){
        User searchUser = new User();
        searchUser.setId(id);
        return !this.userRepository.query(searchUser).isEmpty();
    }

    private boolean checkCatalog(Long id){
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        return !this.catalogRepository.query(searchCatalog).isEmpty();
    }

    private boolean checkCarColor(Long id_car, Long id_color){
        CarColor searchCarColor = new CarColor();
        searchCarColor.setIdCar(id_car);
        searchCarColor.setIdColor(id_color);
        return !this.carColorRepository.query(searchCarColor).isEmpty();
    }

    private boolean checkCarVolume(Long id_car, Long id_volume){
        CarVolume searchCarVolume = new CarVolume();
        searchCarVolume.setIdCar(id_car);
        searchCarVolume.setIdVolume(id_volume);
        return !this.carVolumeRepository.query(searchCarVolume).isEmpty();
    }
}
