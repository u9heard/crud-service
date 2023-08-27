package org.example.models;

public class CarVolume {
    private Long idCar;
    private Long idVolume;

    public CarVolume() {
    }

    public CarVolume(Long idCar, Long idVolume) {
        this.idCar = idCar;
        this.idVolume = idVolume;
    }

    public Long getIdCar() {
        return idCar;
    }

    public void setIdCar(Long idCar) {
        this.idCar = idCar;
    }

    public Long getIdVolume() {
        return idVolume;
    }

    public void setIdVolume(Long idVolume) {
        this.idVolume = idVolume;
    }
}
