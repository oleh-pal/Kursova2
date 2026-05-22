package com.example.kursova.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Orders {
    private final UUID id_order;
    private final UUID id_user;
    private final UUID id_car;
    int total_price;
    @NotBlank
    LocalDateTime date;
    @NotBlank
    String status;
    public Orders(
            @JsonProperty("id_order") UUID id_order,
            @JsonProperty("id_user") UUID id_user,
            @JsonProperty("id_car") UUID id_car,
            @JsonProperty("total_price") int total_price,
            @JsonProperty("date") LocalDateTime date,
            @JsonProperty("status") String status
    ) {
        this.id_order = id_order;
        this.id_user = id_user;
        this.id_car = id_car;
        this.total_price = total_price;
        this.date = date;
        this.status = status;
    }
    public UUID getId_order() {
        return id_order;
    }
    public UUID getId_user() {return id_user;}
    public UUID getId_car() {return id_car;}
    public int getTotal_price() {return total_price;}
    public LocalDateTime getDate() {return date;}
    public String getStatus() {return status;}

}
