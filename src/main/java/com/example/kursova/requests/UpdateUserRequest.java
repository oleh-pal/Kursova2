package com.example.kursova.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserRequest {

    @JsonProperty("name")
    String name;
    @JsonProperty("email")
    String email;
    @JsonProperty("phone")
    String phone;

    public String getName() {
        return name;
    }
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
}
