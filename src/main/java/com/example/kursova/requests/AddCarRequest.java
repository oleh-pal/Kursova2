package com.example.kursova.requests;

import com.example.kursova.models.Car;
import com.example.kursova.models.CarOptions;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddCarRequest {

    @JsonProperty("car")
    private Car car;

    @JsonProperty("carOptions")
    private List<CarOptions> carOptions;
    @Override
    public String toString() {
        return "AddCarRequest{" +
                "car=" + car +
                ", carOptions=" + carOptions +
                '}';
    }
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<CarOptions> getCarOptions() {
        return carOptions;
    }

    public void setCarOptions(List<CarOptions> carOptions) {
        this.carOptions = carOptions;
    }
}
