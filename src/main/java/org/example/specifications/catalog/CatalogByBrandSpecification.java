package org.example.specifications.catalog;

import org.example.interfaces.QuerySpecification;

public class CatalogByBrandSpecification implements QuerySpecification {

    private final String brand;

    public CatalogByBrandSpecification(String brand) {
        this.brand = brand;
    }

    @Override
    public String toSQLClauses() {
        return String.format("brand = '%s'", brand);
    }
}
