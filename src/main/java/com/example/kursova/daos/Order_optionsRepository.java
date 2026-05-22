package com.example.kursova.daos;

import com.example.kursova.models.Order_options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class Order_optionsRepository implements Order_optionsDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Order_optionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertOrder_options(UUID id, Order_options order_options) {
        String sql = "INSERT INTO order_options (id_option, order_id, option_name, order_price) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                id.toString(),
                order_options.getOrder_id().toString(),
                order_options.getOrder_name(),
                order_options.getOption_price()
        );
    }

    @Override
    public List<Order_options> findByOrderId(UUID order_id) {
        String sql = "SELECT * FROM order_options WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{order_id.toString()}, (rs, rowNum) ->
                new Order_options(
                        UUID.fromString(rs.getString("id_option")),
                        UUID.fromString(rs.getString("order_id")),
                        rs.getString("option_name"),
                        rs.getInt("order_price")
                )
        );
    }
    @Override
    public List<Order_options> findByCarId(UUID id_car) {
        String sql = "SELECT o.id_option, o.order_id, o.option_name, o.order_price " +
                "FROM order_options o " +
                "JOIN orders ord ON o.order_id = ord.id_order " +
                "WHERE ord.id_car = ?";
        return jdbcTemplate.query(sql, new Object[]{id_car.toString()}, (rs, rowNum) ->
                new Order_options(
                        UUID.fromString(rs.getString("id_option")),
                        UUID.fromString(rs.getString("order_id")),
                        rs.getString("option_name"),
                        rs.getInt("order_price")
                )
        );
    }
    @Override
    public int deleteByOrderId(UUID order_id) {
        String sql = "DELETE FROM order_options WHERE order_id = ?";
        return jdbcTemplate.update(sql, order_id.toString());
    }

    @Override
    public Optional<Order_options> findById(UUID id_option) {
        String sql = "SELECT * FROM order_options WHERE id_option = ?";
        try {
            Order_options option = jdbcTemplate.queryForObject(sql, new Object[]{id_option.toString()}, (rs, rowNum) ->
                    new Order_options(
                            UUID.fromString(rs.getString("id_option")),
                            UUID.fromString(rs.getString("order_id")),
                            rs.getString("option_name"),
                            rs.getInt("order_price")
                    )
            );
            return Optional.of(option);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}

