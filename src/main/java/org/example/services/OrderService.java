package org.example.services;

import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.*;
import org.example.specifications.carcolor.CarColorByIdsSpecification;
import org.example.specifications.carvolume.CarVolumeByIdsSpecification;
import org.example.specifications.catalog.CatalogByIdSpecification;
import org.example.specifications.user.UserByIdSpecification;

import java.util.List;

public class OrderService {
    CrudRepository<User> userRepository;
    CrudRepository<Catalog> catalogRepository;
    CrudRepository<CarColor> carColorRepository;
    CrudRepository<CarVolume> carVolumeRepository;
    CrudRepository<Order> orderRepository;

    public OrderService(CrudRepository<User> userRepository, CrudRepository<Catalog> catalogRepository, CrudRepository<CarColor> carColorRepository, CrudRepository<CarVolume> carVolumeRepository, CrudRepository<Order> orderRepository) {
        this.userRepository = userRepository;
        this.catalogRepository = catalogRepository;
        this.carColorRepository = carColorRepository;
        this.carVolumeRepository = carVolumeRepository;
        this.orderRepository = orderRepository;
    }

    public boolean add(Order order){
        if(checkUser(order.getIdUser()) && checkCatalog(order.getIdCar()) &&
           checkCarColor(order.getIdCar(), order.getIdColor()) && checkCarVolume(order.getIdCar(), order.getIdVolume())){
            this.orderRepository.save(order);
            return true;
        }
        return false;
    }

    public void delete(QuerySpecification spec){
        this.orderRepository.delete(spec);
    }

    public boolean update(Order order){
        if(checkUser(order.getIdUser()) && checkCatalog(order.getIdCar()) &&
                checkCarColor(order.getIdCar(), order.getIdColor()) && checkCarVolume(order.getIdCar(), order.getIdVolume())){
            this.orderRepository.save(order);
            return true;
        }
        return false;
    }

    public List<Order> get(QuerySpecification spec){
        return this.orderRepository.query(spec);
    }

    private boolean checkUser(Long id){
        QuerySpecification specification = new UserByIdSpecification(id);
        return !this.userRepository.query(specification).isEmpty();
    }

    private boolean checkCatalog(Long id){
        QuerySpecification specification = new CatalogByIdSpecification(id);
        return !this.catalogRepository.query(specification).isEmpty();
    }

    private boolean checkCarColor(Long id_car, Long id_color){
        QuerySpecification specification = new CarColorByIdsSpecification(id_car, id_color);
        return !this.carColorRepository.query(specification).isEmpty();
    }

    private boolean checkCarVolume(Long id_car, Long id_volume){
        QuerySpecification specification = new CarVolumeByIdsSpecification(id_car, id_volume);
        return !this.carVolumeRepository.query(specification).isEmpty();
    }
}
