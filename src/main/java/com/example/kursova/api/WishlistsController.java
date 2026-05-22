package com.example.kursova.api;

import com.example.kursova.models.Car;
import com.example.kursova.models.Users;
import com.example.kursova.models.Wishlist;
import com.example.kursova.services.UserService;
import com.example.kursova.services.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WishlistsController {
    private final UserService userService;
    private final WishlistsService wishlistsService;
    @Autowired
    public WishlistsController(UserService userService, WishlistsService wishlistsService) {
        this.userService = userService;
        this.wishlistsService = wishlistsService;
    }
    @PostMapping("/addWish/{id_car}")
    public ResponseEntity<?> addWish(@PathVariable("id_car") UUID id_car,Authentication auth){
        Optional<Users> user = userService.findByEmail(auth.getName());
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        wishlistsService.AddToWishlist(user.get().getId_users(),id_car);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/getUserWishlist")
    public ResponseEntity<?> getuserWishlist(Authentication auth) {
        Optional<Users> user = userService.findByEmail(auth.getName());
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        List<Car>list = wishlistsService.GetAllWishlistsUser(user.get().getId_users());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/wishlist/ids")
    public ResponseEntity<List<UUID>> getWishlistCarIds(Authentication auth) {
        UUID userId = userService.findByEmail(auth.getName())
                .orElseThrow()
                .getId_users();

        List<UUID> ids = wishlistsService.getWishlistCarIds(userId);
        return ResponseEntity.ok(ids);
    }
    @DeleteMapping("/delete-by-id_car/{id_cars}")
    public int DeleteCar(@PathVariable("id_cars") UUID id_cars){return wishlistsService.DeleteCarFromWishlists(id_cars);}
}
