package com.example.kursova.api;

import com.example.kursova.daos.Order_optionsDao;
import com.example.kursova.models.Order_options;
import com.example.kursova.services.Order_optionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1")
@RestController
public class Order_optionsController {
    private final Order_optionsService ordersService;
    @Autowired
    public Order_optionsController(Order_optionsService ordersService) {this.ordersService = ordersService;}
    @GetMapping("/get-orders-option-byOrderId/{order_id}")
    public List<Order_options> findByOrderId(@PathVariable("order_id") UUID order_id){return ordersService.findByOrderId(order_id);}
    @GetMapping("/get-orders-option-byCarId/{car_id}")
    public List<Order_options> findByCarId(@PathVariable("car_id") UUID id_car){return ordersService.findByCarId(id_car);}
    @GetMapping("/get-orders-option-byId/{id}")
    public Optional<Order_options> findById(@PathVariable("id") UUID id){return ordersService.findById(id);}
    @DeleteMapping("/delete-ByOrderId{order_id}")
    public int deleteByOrderId(@PathVariable("order_id")UUID order_id){return ordersService.deleteByOrderId(order_id);}


}
