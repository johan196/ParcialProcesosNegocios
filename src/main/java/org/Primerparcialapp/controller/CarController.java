package org.Primerparcialapp.controller;

import org.Primerparcialapp.model.Car;
import org.Primerparcialapp.model.CarResponse;
import org.Primerparcialapp.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService car) {
        this.carService = car;
    }

    @PostMapping
    public CarResponse createCars() {
        return this.carService.create();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCarById(@PathVariable(name = "id") Long id) {
        Map response = new HashMap<>();
        try {
            response.put("Car", this.carService.getById(id));
            return new ResponseEntity<Map>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "No se encontro el id proporcionado");
            response.put("data", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> geAllCars() {
        Map response = new HashMap<>();
        try {
            response.put("cars", this.carService.getAll());
            return new ResponseEntity<Map>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "No se obtuvieron los usuarios");
            response.put("data", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> createCars(@RequestBody Car car) {
        Map response = new HashMap<>();
        try {
            response.put("Car", this.carService.update(car.getId(),car));
            return new ResponseEntity<Map>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "No se encontro el id proporcionado");
            response.put("data", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
