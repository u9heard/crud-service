package org.example.specifications.color;

import org.example.interfaces.QuerySpecification;

public class ColorByNameSpecification implements QuerySpecification {
    private String name;

    public ColorByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSQLClauses() {
        return String.format("color = '%s'", name);
    }
}
