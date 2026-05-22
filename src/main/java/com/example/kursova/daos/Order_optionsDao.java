package com.example.kursova.daos;

import com.example.kursova.models.Order_options;
import com.example.kursova.models.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Order_optionsDao {
    int insertOrder_options(UUID id, Order_options order_options);
    default int insertOrder_options(Order_options order_options){
        UUID id = UUID.randomUUID();
        return insertOrder_options(id, order_options);
    };
    List<Order_options> findByOrderId(UUID order_id);
    List<Order_options> findByCarId(UUID id_car);
    Optional<Order_options> findById(UUID id_option);
    int deleteByOrderId(UUID order_id);

}
