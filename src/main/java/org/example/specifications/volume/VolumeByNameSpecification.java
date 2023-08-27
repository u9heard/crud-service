package org.example.specifications.volume;

import org.example.interfaces.QuerySpecification;

public class VolumeByNameSpecification implements QuerySpecification {
    private Double name;

    public VolumeByNameSpecification(Double name) {
        this.name = name;
    }

    @Override
    public String toSQLClauses() {
        return String.format("vol = %s", name);
    }
}
