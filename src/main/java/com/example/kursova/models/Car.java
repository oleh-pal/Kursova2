package com.example.kursova.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Car {
    @NotBlank
    private String marka;
    @NotBlank
    private String model;
    @NotBlank
    private String color;
    @Min(1886)
    private int year;
    @NotBlank
    private String engine_type;
    private int engine_power;
    private int doors;
    private int price;
    @NotBlank
    private String transmission;
    @NotBlank
    private String image;
    @NotBlank
    private String body_type;
    @NotBlank
    private String description;
    @NotBlank
    private String  condition;
    private String additionalPhotos;
    private final UUID id_cars;


    public Car(@JsonProperty("marka") String marka,
               @JsonProperty("model") String model,
               @JsonProperty("color") String color,
               @JsonProperty("year") int year,
               @JsonProperty("engine_type") String engine_type,
               @JsonProperty("engine_power") int engine_power,
               @JsonProperty("doors") int doors,
               @JsonProperty("price") int price,
               @JsonProperty("transmission") String transmission,
               @JsonProperty("image") String image,
               @JsonProperty("body_type") String body_type,
               @JsonProperty("description") String description,
               @JsonProperty("condition") String condition,
               @JsonProperty("photos") String additionalPhotos,
               @JsonProperty("id_cars") UUID id_cars
    ) {
        this.marka = marka;
        this.model = model;
        this.color = color;
        this.year = year;
        this.engine_type = engine_type;
        this.engine_power = engine_power;
        this.doors = doors;
        this.price = price;
        this.transmission = transmission;
        this.image = image;
        this.body_type = body_type;
        this.description = description;
        this.condition = condition;
        this.additionalPhotos = additionalPhotos;
        this.id_cars = id_cars;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {return year;}

    public String getEngine_type() {
        return engine_type;
    }

    public int getEngine_power() {
        return engine_power;
    }

    public int getDoors() {
        return doors;
    }

    public int getPrice() {
        return price;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getImage() {
        return image;
    }

    public String getBody_type() {
        return body_type;
    }

    public String getDescription() {
        return description;
    }
    public String getCondition() {return condition;}
    public UUID getId_cars() {
        return id_cars;
    }

    public void setImage(String s) {
        this.image = s;
    }
    public String getAdditionalPhotosArray(){return additionalPhotos;}

    public void setAdditionalPhotosArray(String photos) {
        this.additionalPhotos = photos;
    }
}
