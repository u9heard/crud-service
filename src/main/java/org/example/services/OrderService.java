package org.example.services;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.interfaces.StorageService;
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
        throw new ModelConflictException("Unique check failed");
    }

    public void update(Order order){
        if(checkUser(order.getIdUser()) && checkCatalog(order.getIdCar()) &&
                checkCarColor(order.getIdCar(), order.getIdColor()) && checkCarVolume(order.getIdCar(), order.getIdVolume())){
            this.modelRepository.save(order);
        }
        throw new ModelConflictException("Unique check failed");
    }

    private boolean checkUser(Long id){
        return !this.userRepository.query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id))).isEmpty();
    }

    private boolean checkCatalog(Long id){
        return !this.catalogRepository.query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id))).isEmpty();
    }

    private boolean checkCarColor(Long id_car, Long id_color){
        return !this.carColorRepository.query(List.of(
                new SearchCriteria("id_car", SearchOperator.EQUALS, id_car),
                new SearchCriteria("id_color", SearchOperator.EQUALS, id_color))).isEmpty();
    }

    private boolean checkCarVolume(Long id_car, Long id_volume){
        return !this.carColorRepository.query(List.of(
                new SearchCriteria("id_car", SearchOperator.EQUALS, id_car),
                new SearchCriteria("id_volume", SearchOperator.EQUALS, id_volume))).isEmpty();
    }
}
