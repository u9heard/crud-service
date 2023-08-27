package org.example.specifications.carcolor;

import org.example.interfaces.QuerySpecification;

public class CarColorByCarIdSpecification implements QuerySpecification {
    private Long id_car;

    public CarColorByCarIdSpecification(Long id) {
        this.id_car = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id_car = %d", id_car);
    }
}
