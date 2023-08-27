package org.example.specifications.order;

import org.example.interfaces.QuerySpecification;

public class OrderByUserSpecification implements QuerySpecification {
    private Long userId;

    public OrderByUserSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id_user = %s", userId);
    }
}
