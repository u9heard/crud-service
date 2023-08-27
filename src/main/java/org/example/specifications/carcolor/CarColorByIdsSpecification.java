package org.example.specifications.carcolor;

import org.example.interfaces.QuerySpecification;

public class CarColorByIdsSpecification implements QuerySpecification {
    private Long id_car;
    private Long id_color;

    public CarColorByIdsSpecification(Long id_car, Long id_color) {
        this.id_car = id_car;
        this.id_color = id_color;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id_car = %d and id_color = %d", id_car, id_color);
    }
}
