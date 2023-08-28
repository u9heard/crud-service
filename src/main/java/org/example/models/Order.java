package org.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class Order {
    private Long id;
    private Long idUser;
    private Long idCar;
    private Long idVolume;
    private Long idColor;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBuy;

    public Order() {
        this.id = 0L;
    }

    public Order(Long id, Long idUser, Long idCar, Long idVolume, Long idColor, LocalDate dateBuy) {
        this.id = id;
        this.idUser = idUser;
        this.idCar = idCar;
        this.idVolume = idVolume;
        this.idColor = idColor;
        this.dateBuy = dateBuy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public LocalDate getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(LocalDate dateBuy) {
        this.dateBuy = dateBuy;
    }
}
