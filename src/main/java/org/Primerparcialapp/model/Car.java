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

}