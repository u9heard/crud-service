package org.example.specifications.catalog;

import org.example.interfaces.QuerySpecification;

public class CatalogByBrandModelSpecification implements QuerySpecification {
    private String brand;
    private String model;

    public CatalogByBrandModelSpecification(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toSQLClauses() {
        return String.format("brand = '%s' and model = '%s'", brand, model);
    }
}
