package com.example.kursova.requests;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {
    @JsonProperty("id_user")
    private UUID id_user;
    @JsonProperty("id_car")
    private UUID id_car;
    @JsonProperty("totalPrice")
    private int totalPrice;
    //private String status;
    @JsonProperty("orderOptions")
    private List<OrderOptionDTO> options;

    // геттери/сеттери
    public UUID getId_user() { return id_user; }
    public void setId_user(UUID id_user) { this.id_user = id_user; }

    public UUID getId_car() { return id_car; }
    public void setId_car(UUID id_car) { this.id_car = id_car; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }

    public List<OrderOptionDTO> getOptions() { return options; }
    public void setOptions(List<OrderOptionDTO> options) { this.options = options; }

    public static class OrderOptionDTO {
        private String optionName;
        private int orderPrice;

        public String getOptionName() { return optionName; }
        public void setOptionName(String optionName) { this.optionName = optionName; }

        public int getOrderPrice() { return orderPrice; }
        public void setOrderPrice(int orderPrice) { this.orderPrice = orderPrice; }
    }
}

