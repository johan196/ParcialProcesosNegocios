package org.Primerparcialapp.service;

import lombok.extern.slf4j.Slf4j;
import org.Primerparcialapp.model.Car;
import org.Primerparcialapp.model.CarResponse;
import org.Primerparcialapp.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CarServiceImpl extends AbstractClient  implements CarService{

    protected CarServiceImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Autowired
    private CarRepository carRepository;

    @Override
    public CarResponse create() {
        String uri = baseUrl + "/cars";
        System.out.println();
        try {
            ResponseEntity<CarResponse> response = restTemplate.getForEntity(uri, CarResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                List<Car> cars = Objects.requireNonNull(response.getBody()).getCar();
                carRepository.saveAll(cars);
                return response.getBody();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //log.error("Error in user creation - httpStatus was: {}", response.getStatusCode());
        throw new RuntimeException("Error");
    }

    @Override
    public Car getById(Long id) {
        Car car1 = carRepository.findById(id).get();
        if(car1 != null)
        {
            return car1;
        }
        throw new RuntimeException("Error");
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car update(Long id, Car car) {
        Car car1 = carRepository.findById(id).get();
        car1.setCar(car.getCar());
        car1.setCar_model(car.getCar_model());
        car1.setCar_vin(car.getCar_vin());
        car1.setAvailability(car.getAvailability());
        car1.setCar_model_year(car.getCar_model_year());
        car1.setCar_color(car.getCar_color());
        car1.setPrice(car.getPrice());
        return carRepository.save(car1);
    }

}
