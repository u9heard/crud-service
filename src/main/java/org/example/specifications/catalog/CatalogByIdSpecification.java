package org.example.specifications.catalog;

import org.example.interfaces.QuerySpecification;

public class CatalogByIdSpecification implements QuerySpecification {

    private Long id;

    public CatalogByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d", id);
    }
}
