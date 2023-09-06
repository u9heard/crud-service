ALTER TABLE car_color
ADD CONSTRAINT car_color_uniq UNIQUE(id_car, id_color);

ALTER TABLE car_volume
ADD CONSTRAINT car_volume_uniq UNIQUE(id_car, id_volume);