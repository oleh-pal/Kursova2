package com.example.kursova.models;

import java.util.UUID;

public class Wishlist {
    private UUID id;
    private UUID id_cars;
    private UUID id_users;
    public Wishlist(UUID id, UUID id_cars, UUID id_users) {
        this.id = id;
        this.id_cars = id_cars;
        this.id_users = id_users;
    }
    public UUID getId() {return id;}
    public UUID getId_cars() {return id_cars;}
    public UUID getId_users() {return id_users;}

}
