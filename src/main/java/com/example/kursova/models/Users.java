package com.example.kursova.models;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
public class Users {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String role;
    private String phone;
    private boolean emailConfirmed;
    private UUID id_users;

    public  Users(@JsonProperty("name") String name,
                  @JsonProperty("email") String email,
                  @JsonProperty("password") String password,
                  String role,
                  @JsonProperty("phone") String phone,
                  boolean emailConfirmed,
                  @JsonProperty("id_users") UUID id_users
    )
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.emailConfirmed = emailConfirmed;
        this.id_users = id_users;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getRole(){return role;}
    public String getPhone(){return phone;}
    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }
    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public UUID getId_users(){return id_users;}

}
