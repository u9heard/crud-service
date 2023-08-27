package org.example.specifications.carvolume;

import org.example.interfaces.QuerySpecification;

public class CarVolumeByIdsSpecification implements QuerySpecification {
    private Long idCar;
    private Long idVolume;

    public CarVolumeByIdsSpecification(Long idCar, Long idVolume) {
        this.idCar = idCar;
        this.idVolume = idVolume;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id_car = %s and id_volume = %s", idCar, idVolume);
    }
}
