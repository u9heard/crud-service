package org.example.specifications;

import org.example.interfaces.QuerySpecification;

public class DefaultByIdSpecification implements QuerySpecification {
    Long id;

    public DefaultByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d", id);
    }
}
