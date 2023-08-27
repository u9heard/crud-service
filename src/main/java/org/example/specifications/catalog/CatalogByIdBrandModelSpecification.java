package org.example.specifications.catalog;

import org.example.interfaces.QuerySpecification;

public class CatalogByIdBrandModelSpecification implements QuerySpecification {
    private Long id;
    private String brand;
    private String model;

    public CatalogByIdBrandModelSpecification(Long id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toSQLClauses() {
        return String.format("id = %d and brand = '%s' and model = '%s'", id, brand, model);
    }
}
