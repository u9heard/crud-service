package org.example.models;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Catalog {
    private Long id;
    private String brand;
    private String model;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate release_date;
    private BigDecimal price;

    public Catalog(){
        this.id = 0L;
    }

    public Catalog(Long id, String brand, String model, LocalDate release_date, BigDecimal price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.release_date = release_date;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
