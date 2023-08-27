package org.example.models;

import java.sql.Date;

public class Order {
    private Long id;
    private Long idUser;
    private Long idCar;
    private Long idVolume;
    private Long idColor;
    private Date dateBuy;

    public Order() {
        this.id = 0L;
    }

    public Order(Long id, Long idUser, Long idCar, Long idVolume, Long idColor, Date dateBuy) {
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

    public Date getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(Date dateBuy) {
        this.dateBuy = dateBuy;
    }
}
