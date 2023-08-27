package org.example.specifications.user;

import org.example.interfaces.QuerySpecification;

public class UserByIdSpecification implements QuerySpecification {
    private final Long id;

    public UserByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d", id);
    }
}
