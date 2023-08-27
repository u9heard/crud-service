package org.example.specifications.order;

import org.example.interfaces.QuerySpecification;

public class OrderByIdSpecification implements QuerySpecification {
    private Long id;

    public OrderByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d", id);
    }
}
