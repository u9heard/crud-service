package org.example.models;

public class Volume {
    private Long id;
    private Double volume;

    public Volume(){

    }

    public Volume(Long id, Double volume) {
        this.id = id;
        this.volume = volume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
