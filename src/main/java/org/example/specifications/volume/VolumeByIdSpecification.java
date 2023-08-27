package org.example.specifications.volume;

import org.example.interfaces.QuerySpecification;

public class VolumeByIdSpecification implements QuerySpecification {

    private Long id;

    public VolumeByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d", id);
    }
}
