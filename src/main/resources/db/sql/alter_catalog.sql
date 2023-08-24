-- liquibase formatted sql

-- changeset liquibase:3

ALTER TABLE catalog
ALTER COLUMN price TYPE DECIMAL(14, 4);