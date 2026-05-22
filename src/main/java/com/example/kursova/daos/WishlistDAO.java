package com.example.kursova.daos;

import com.example.kursova.models.Car;
import com.example.kursova.models.Wishlist;

import java.util.List;
import java.util.UUID;

public interface WishlistDAO {
    int insert(Wishlist wishlist);
    List<Car> GetAllWishlistsUser(UUID id_user);
    List<UUID> getWishlistCarIds(UUID userId);
    int DeleteCarFromWishlists(UUID id);
}
