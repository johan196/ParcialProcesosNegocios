package org.Primerparcialapp.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "car")
    private String car;
    @Column(name = "car_model")
    private String car_model;
    @Column(name = "car_color")
    private String car_color;
    @Column(name = "car_model_year")
    private int car_model_year;
    @Column(name = "car_vin")
    private String car_vin;
    @Column(name = "price")
    private String price;
    @Column(name = "availability")
    private Boolean availability;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public int getCar_model_year() {
        return car_model_year;
    }

    public void setCar_model_year(int car_model_year) {
        this.car_model_year = car_model_year;
    }

    public String getCar_vin() {
        return car_vin;
    }

    public void setCar_vin(String car_vin) {
        this.car_vin = car_vin;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}