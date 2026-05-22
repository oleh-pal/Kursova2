package com.example.kursova.daos;

import com.example.kursova.models.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersDao {
    int insertOrder(UUID id, Orders order);
    default int insertOrder(Orders order){
        UUID id = UUID.randomUUID();
        return insertOrder(id, order);
    };
    Optional<Orders> findById(UUID id_order);
    List<Orders> findByUserId(UUID id_user);
    List<Orders> findByCarId(UUID id_car);
    List<Orders> findAll();
    int updateStatus(UUID id_order, String newStatus);
    int updateTotalPrice(UUID id_order, int totalPrice);
    int deleteOrder(UUID id_order);
}
