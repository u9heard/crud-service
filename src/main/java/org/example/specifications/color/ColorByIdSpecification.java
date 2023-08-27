package org.example.specifications.color;

import org.example.interfaces.QuerySpecification;

public class ColorByIdSpecification implements QuerySpecification {

    private Long id;

    public ColorByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d", id);
    }
}
