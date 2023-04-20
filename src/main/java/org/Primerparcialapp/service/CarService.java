package org.Primerparcialapp.service;

import org.Primerparcialapp.model.Car;
import org.Primerparcialapp.model.CarResponse;

import java.util.List;

public interface CarService {

    CarResponse create();
    Car getById(Long id);
    List<Car> getAll();
    Car update(Long id, Car car);

}
