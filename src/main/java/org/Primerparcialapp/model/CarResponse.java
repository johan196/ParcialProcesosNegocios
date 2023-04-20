package org.Primerparcialapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CarResponse {

    @JsonProperty("cars")
    private List<Car> car;

    public List<Car> getCar() {
        return car;
    }
}