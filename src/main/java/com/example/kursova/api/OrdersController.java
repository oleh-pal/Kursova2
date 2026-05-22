package com.example.kursova.api;

import com.example.kursova.models.Car;
import com.example.kursova.models.Users;
import com.example.kursova.requests.CreateOrderRequest;
import com.example.kursova.requests.StatusUpdateRequest;
import com.example.kursova.models.Orders;
import com.example.kursova.services.CarService;
import com.example.kursova.services.EmailService;
import com.example.kursova.services.OrdersService;
import com.example.kursova.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1")
@RestController
public class OrdersController {
    private final OrdersService ordersService;
    private final CarService carService;
    private final EmailService emailService;
    private final UserService userService;
    private final UUID errorId = UUID.fromString("4397aa8c-9acd-4352-aeac-65177a257ecb");

    @Autowired
    public OrdersController(OrdersService ordersService, CarService carService, EmailService emailService, UserService userService) {
        this.ordersService = ordersService;
        this.carService = carService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            UUID status = ordersService.createOrderWithOptions(request);
            if (status != null && status.equals(errorId)) {
                return ResponseEntity.status(500).body("На даний автомобіль не можна оформити замовлення, оскільки він вже замовлений ");
            } else {
                return ResponseEntity.status(200).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Помилка при створенні замовлення: " + e.getMessage());

        }
    }

    @GetMapping("/get-all-orders")
    public List<Orders> getAll() {
        return ordersService.getAll();
    }

    @GetMapping("/get-order-by-order_id/{id}")
    public Optional<Orders> GetOrderById(@PathVariable("id") UUID id) {
        return ordersService.getById(id);
    }

    @GetMapping("/get-order-by-user_id/{id}")
    public List<Orders> GetOrderByUser_Id(@PathVariable("id") UUID id) {


        return ordersService.getByUserId(id);
    }

    @GetMapping("/get-order-by-car_id/{id}")
    public List<Orders> GetOrderByCar_Id(@PathVariable("id") UUID id) {
        return ordersService.getByCarId(id);
    }

    //@PutMapping("/update-status/{id_order}")
    // public int updateStatus(@PathVariable("id_order") UUID id,@RequestBody String newStatus){return ordersService.updateStatus(id,newStatus);}
    @PutMapping("/update-status/{id_order}")
    public int updateStatus(@PathVariable("id_order") UUID id,
                            @RequestBody StatusUpdateRequest request) {
        return ordersService.updateStatus(id, request.getStatus());
    }

    @PutMapping("/update-totalPrice/{id_order}")
    public int updateTotalPrice(@PathVariable("id_order") UUID id, @RequestBody int totalPrice) {
        return ordersService.updateTotalPrice(id, totalPrice);
    }

    @DeleteMapping("/delete-order-by-id{id_order}")
    public int deleteOrder(@PathVariable("id_order") UUID id) {
        return ordersService.deleteOrder(id);
    }
}
