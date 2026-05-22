package com.example.kursova.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordRequest {

    @JsonProperty("newPassword")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }
}

