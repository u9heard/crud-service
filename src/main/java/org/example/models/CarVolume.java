package org.example.models;

public class CarVolume {
    private Long id;
    private Long idCar;
    private Long idVolume;

    public CarVolume() {
    }

    public CarVolume(Long id, Long idCar, Long idVolume) {
        this.id = id;
        this.idCar = idCar;
        this.idVolume = idVolume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
