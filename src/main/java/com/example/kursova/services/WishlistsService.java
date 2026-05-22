package com.example.kursova.services;

import com.example.kursova.daos.WishlistDAO;
import com.example.kursova.models.Car;
import com.example.kursova.models.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistsService {
    private final WishlistDAO wishlistDAO;

    @Autowired
    public WishlistsService(WishlistDAO wishlistDAO) {
        this.wishlistDAO = wishlistDAO;
    }

    public List<Car> GetAllWishlistsUser(UUID id_user) {
        return wishlistDAO.GetAllWishlistsUser(id_user);
    }
    public List<UUID> getWishlistCarIds(UUID id_user) {return wishlistDAO.getWishlistCarIds(id_user);}
    public int AddToWishlist(UUID id_user, UUID id_car) {
        UUID id = UUID.randomUUID();
        return wishlistDAO.insert(new Wishlist(id, id_car, id_user));
    }
    public int DeleteCarFromWishlists(UUID id){return wishlistDAO.DeleteCarFromWishlists(id);}
}
