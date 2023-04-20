package org.Primerparcialapp.repository;

import org.Primerparcialapp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
