package org.example.models;

public class CarColor {
    private Long id;
    private Long idCar;
    private Long idColor;

    public CarColor() {
    }

    public CarColor(Long id, Long idCar, Long idColor) {
        this.id = id;
        this.idCar = idCar;
        this.idColor = idColor;
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

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }
}
