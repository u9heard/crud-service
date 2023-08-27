package org.example.specifications.carvolume;

import org.example.interfaces.QuerySpecification;

public class CarVolumeByIdCarSpecification implements QuerySpecification {
    private Long idCar;

    public CarVolumeByIdCarSpecification(Long idCar) {
        this.idCar = idCar;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id_car = %s", idCar);
    }
}
