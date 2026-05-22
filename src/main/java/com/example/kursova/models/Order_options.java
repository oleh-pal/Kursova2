package com.example.kursova.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Order_options {
    UUID id_option;
    UUID order_id;
    String option_name;
    int option_price;
    public Order_options(
            @JsonProperty("id_option") UUID id_option,
            @JsonProperty("order_id") UUID order_id,
            @JsonProperty("option_name") String option_name,
            @JsonProperty("option_price") int option_price

    ) {
        this.id_option = id_option;
        this.order_id = order_id;
        this.option_name =option_name;
        this.option_price = option_price;
    }
    public UUID get_id_option(){return id_option;}
    public UUID getOrder_id(){return order_id;}
    public String getOrder_name(){return option_name;}
    public int getOption_price(){return option_price;}
}
