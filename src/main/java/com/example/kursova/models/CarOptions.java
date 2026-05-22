package com.example.kursova.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CarOptions {
    UUID id_option;
    UUID car_id;
    String option_name;
    int option_price;

    public CarOptions(
            @JsonProperty("id_option") UUID id_option,
            @JsonProperty("car_id") UUID car_id,
            @JsonProperty("option_name") String option_name,
            @JsonProperty("option_price") int option_price
    ) {
        this.id_option = id_option;
        this.car_id = car_id;
        this.option_name = option_name;
        this.option_price = option_price;

    }

    public UUID getId_option() {
        return id_option;
    }
    public UUID getCar_id() {return car_id;}
    public String getOption_name() {return option_name;}
    public int getOption_price() {return option_price;}
    public void setId_car(UUID car_id) {this.car_id = car_id;}
}
