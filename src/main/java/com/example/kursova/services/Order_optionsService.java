package com.example.kursova.services;

import com.example.kursova.daos.Order_optionsDao;
import com.example.kursova.models.Order_options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Order_optionsService {
    private final Order_optionsDao ordersDao;
    @Autowired
    public Order_optionsService(Order_optionsDao ordersDao) {this.ordersDao = ordersDao;}
    public int AddOrderOption(Order_options order_options){return ordersDao.insertOrder_options(order_options);}
    public List<Order_options> findByOrderId(UUID order_id){return ordersDao.findByOrderId(order_id);}
    public List<Order_options> findByCarId(UUID id_car){return ordersDao.findByCarId(id_car);}
    public Optional<Order_options> findById(UUID id_option){return ordersDao.findById(id_option);}
    public int deleteByOrderId(UUID order_id){return ordersDao.deleteByOrderId(order_id);}
}
