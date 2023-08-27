package org.example.models;

public class CarColor {
    private Long idCar;
    private Long idColor;

    public CarColor() {
    }

    public CarColor(Long idCar, Long idColor) {
        this.idCar = idCar;
        this.idColor = idColor;
    }

    public Long getIdCar() {
        return idCar;
    }

    public void setIdCar(Long idCar) {
        this.idCar = idCar;
    }

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }
}
