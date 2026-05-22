package com.example.kursova.daos;

import com.example.kursova.models.Car;
import com.example.kursova.models.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class WishlistsRepository implements WishlistDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public WishlistsRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
    @Override
    public int insert(Wishlist wishlist) {
        return jdbcTemplate.update(
                "INSERT INTO wishlists (idwishlists, id_user,id_car) VALUES (?,?,?)",
                wishlist.getId().toString(),
                wishlist.getId_users().toString(),
                wishlist.getId_cars().toString()
        );
    }

    @Override
    public List<Car> GetAllWishlistsUser(UUID id_user) {
        String sql = "SELECT c.*  FROM cars c  JOIN wishlists w  ON w.id_car = c.id_cars  WHERE w.id_user = ?";
        return jdbcTemplate.query(sql, new Object[]{id_user.toString()}, (resultSet, rowNum) -> {
            UUID id = UUID.fromString(resultSet.getString("id_cars"));
            String marka = resultSet.getString("marka");
            int year = resultSet.getInt("year");
            String color = resultSet.getString("color");
            String model = resultSet.getString("model");
            String body_type = resultSet.getString("body_type");
            String engine = resultSet.getString("engine_type");
            String transmission = resultSet.getString("transmission");
            String image = resultSet.getString("image");
            int price = resultSet.getInt("price");
            int doors = resultSet.getInt("doors");
            int engine_power = resultSet.getInt("engine_power");
            String description = resultSet.getString("description");
            String condition = resultSet.getString("condition");
            String additionalPhotosArray = resultSet.getString("additionalPhotos");
            return new Car(marka,model,color,year,engine,engine_power,doors,price,transmission,image,body_type,description,condition,additionalPhotosArray,id);
        });
    }
    @Override
    public List<UUID> getWishlistCarIds(UUID userId) {
        String sql = "SELECT id_car FROM wishlists WHERE id_user = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{ userId.toString() },
                (rs, rowNum) -> UUID.fromString(rs.getString("id_car"))
        );
    }
    @Override
    public int DeleteCarFromWishlists(UUID id) {
        String sql = "DELETE FROM wishlists WHERE id_car = ?";
        Object[] args = new Object[] {id.toString()};
        int row = jdbcTemplate.update(sql, args);
        return row;
    }
}
