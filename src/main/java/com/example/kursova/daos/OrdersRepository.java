package com.example.kursova.daos;

import com.example.kursova.models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrdersRepository implements OrdersDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrdersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertOrder(UUID id, Orders order) {
        return jdbcTemplate.update(
                "INSERT INTO orders (id_order, id_user, id_car, total_price, date, status) VALUES (?, ?, ?, ?, ?, ?)",
                id.toString(),
                order.getId_user().toString(),
                order.getId_car().toString(),
                order.getTotal_price(),
                order.getDate(),
                order.getStatus()
        );
    }

    @Override
    public Optional<Orders> findById(UUID id_order) {
        String sql = "SELECT * FROM orders WHERE id_order = ?";
        try {
            Orders order = jdbcTemplate.queryForObject(sql, new Object[]{id_order.toString()}, (rs, rowNum) ->
                    new Orders(
                            UUID.fromString(rs.getString("id_order")),
                            UUID.fromString(rs.getString("id_user")),
                            UUID.fromString(rs.getString("id_car")),
                            rs.getInt("total_price"),
                            rs.getObject("date", LocalDateTime.class),
                            rs.getString("status")
                    )
            );
            return Optional.ofNullable(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Orders> findByUserId(UUID id_user) {
        String sql = "SELECT * FROM orders WHERE id_user = ?";
        return jdbcTemplate.query(sql, new Object[]{id_user.toString()}, (rs, rowNum) ->
                new Orders(
                        UUID.fromString(rs.getString("id_order")),
                        UUID.fromString(rs.getString("id_user")),
                        UUID.fromString(rs.getString("id_car")),
                        rs.getInt("total_price"),
                        rs.getObject("date", LocalDateTime.class),
                        rs.getString("status")
                )
        );
    }

    @Override
    public List<Orders> findByCarId(UUID id_car) {
        String sql = "SELECT * FROM orders WHERE id_car = ?";
        return jdbcTemplate.query(sql, new Object[]{id_car.toString()}, (rs, rowNum) ->
                new Orders(
                        UUID.fromString(rs.getString("id_order")),
                        UUID.fromString(rs.getString("id_user")),
                        UUID.fromString(rs.getString("id_car")),
                        rs.getInt("total_price"),
                        rs.getObject("date", LocalDateTime.class),
                        rs.getString("status")
                )
        );
    }

    @Override
    public List<Orders> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Orders(
                        UUID.fromString(rs.getString("id_order")),
                        UUID.fromString(rs.getString("id_user")),
                        UUID.fromString(rs.getString("id_car")),
                        rs.getInt("total_price"),
                        rs.getObject("date", LocalDateTime.class),
                        rs.getString("status")
                )
        );

    }

    @Override
    public int updateStatus(UUID id_order, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id_order = ?";
        return jdbcTemplate.update(sql, newStatus, id_order.toString());
    }

    @Override
    public int updateTotalPrice(UUID id_order, int totalPrice) {
        String sql = "UPDATE orders SET total_price = ? WHERE id_order = ?";
        return jdbcTemplate.update(sql, totalPrice, id_order.toString());
    }

    @Override
    public int deleteOrder(UUID id_order) {
        String sql = "DELETE FROM orders WHERE id_order = ?";
        return jdbcTemplate.update(sql, id_order.toString());
    }
}
