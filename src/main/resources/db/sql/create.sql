-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE users(
id bigserial PRIMARY KEY,
name VARCHAR(128) NOT NULL,
surname VARCHAR(128) NOT NULL,
father_name VARCHAR(128) NOT NULL,
DOB DATE NOT NULL,
sex VARCHAR(8) NOT NULL,
CONSTRAINT MaleFemaleCheck CHECK (sex = 'Male' OR sex = 'Female')
);

CREATE TABLE catalog(
id bigserial PRIMARY KEY,
brand VARCHAR(64) NOT NULL,
model VARCHAR(64) NOT NULL,
release_date DATE NOT NULL,
price MONEY
);

ALTER TABLE catalog
ADD CONSTRAINT brand_model_uniq UNIQUE(brand, model);

CREATE TABLE volume(
id bigserial PRIMARY KEY,
vol FLOAT UNIQUE NOT NULL
);

CREATE TABLE color(
id bigserial PRIMARY KEY,
color VARCHAR(16) UNIQUE NOT NULL
);

CREATE TABLE car_color(
id_car bigserial NOT NULL,
id_color bigserial NOT NULL,
PRIMARY KEY(id_car, id_color),
FOREIGN KEY (id_car) REFERENCES catalog(id) ON DELETE CASCADE,
FOREIGN KEY (id_color) REFERENCES color(id) ON DELETE CASCADE
);

CREATE TABLE car_volume(
id_car bigserial NOT NULL,
id_volume bigserial NOT NULL,
PRIMARY KEY(id_car, id_volume),
FOREIGN KEY (id_car) REFERENCES catalog(id) ON DELETE CASCADE,
FOREIGN KEY (id_volume) REFERENCES volume(id) ON DELETE CASCADE
);

CREATE TABLE orders(
id bigserial PRIMARY KEY,
id_user bigserial,
id_car bigserial,
id_vol bigserial,
id_color bigserial,
date_buy DATE NOT NULL,
FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (id_car) REFERENCES catalog(id) ON DELETE SET NULL,
FOREIGN KEY (id_vol) REFERENCES volume(id) ON DELETE SET NULL,
FOREIGN KEY (id_color) REFERENCES color(id) ON DELETE SET NULL
);