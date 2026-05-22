package com.example.kursova.services;

import com.example.kursova.models.Car;
import com.example.kursova.models.Users;
import com.example.kursova.requests.CreateOrderRequest;
import com.example.kursova.daos.OrdersDao;
import com.example.kursova.models.Order_options;
import com.example.kursova.models.Orders;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OrdersService {
    private final OrdersDao ordersDao;
    private final Order_optionsService orderOptionService;
    private final CarService carService;
    private final EmailService emailService;
    private final UserService userService;
    private final UUID errorId = UUID.fromString("4397aa8c-9acd-4352-aeac-65177a257ecb");

    @Autowired
    public OrdersService(OrdersDao ordersDao, Order_optionsService orderOptionServic, CarService carService, EmailService emailService, UserService userService)   {
        this.ordersDao = ordersDao;
        this.orderOptionService = orderOptionServic;
        this.carService = carService;
        this.emailService = emailService;
        this.userService = userService;

    }
    @Transactional
    public UUID createOrderWithOptions(CreateOrderRequest request) {
        Orders order = new Orders(
                UUID.randomUUID(),
                request.getId_user(),
                request.getId_car(),
                request.getTotalPrice(),
                //Timestamp.valueOf(LocalDateTime.now()),
                LocalDateTime.now(ZoneId.of("Europe/Kiev")),
                "pending"
        );
        List<Orders> ordersList =ordersDao.findByCarId(request.getId_car());
       if(!ordersList.isEmpty()){return errorId;}
        ordersDao.insertOrder(order.getId_order(), order);
        if (request.getOptions() != null) {
            for (CreateOrderRequest.OrderOptionDTO option : request.getOptions()) {
                Order_options orderOption = new Order_options(
                        UUID.randomUUID(),
                        order.getId_order(),
                        option.getOptionName(),
                        option.getOrderPrice()
                );
                orderOptionService.AddOrderOption(orderOption);
            }
        }
        UUID id_car=ordersDao.findById(order.getId_order()).get().getId_car();
        UUID id_user=ordersDao.findById(order.getId_order()).get().getId_user();
        Optional <Users>  optionalUser = userService.findById(id_user);
        Users user=optionalUser.get();
        Car car =carService.GetCarById(id_car).get();
        String marka=car.getMarka();
        String model=car.getModel();
        String orderCar=marka+" "+model;
        String status=getStatus(order.getStatus());
        List<Order_options> order_options=orderOptionService.findByOrderId(order.getId_order());
        try {
            emailService.sendConfirmOrderEmail(user.getEmail(),status,user.getName(),orderCar,order_options,request.getTotalPrice());
        } catch (MessagingException e) {
            e.printStackTrace();

        }




        return order.getId_order();
    }
    public int AddOrder(Orders order){return ordersDao.insertOrder(order);}
    public Optional<Orders> getById(UUID id_order){return ordersDao.findById(id_order);}
    public List<Orders> getByUserId(UUID id_user){return ordersDao.findByUserId(id_user);}
    public List<Orders> getByCarId(UUID id_car){return ordersDao.findByCarId(id_car);}
    public List<Orders> getAll(){return ordersDao.findAll();}
    public int updateStatus(UUID id_order, String newStatus){
        UUID id_car=ordersDao.findById(id_order).get().getId_car();
        UUID id_user=ordersDao.findById(id_order).get().getId_user();
        Optional <Users>  optionalUser = userService.findById(id_user);
        Users user=optionalUser.get();
        Car car =carService.GetCarById(id_car).get();
        String marka=car.getMarka();
        String model=car.getModel();
        String orderCar=marka+" "+model;
        String status=getStatus(newStatus);
        try {
            emailService.sendStatusEmail(user.getEmail(),status, user.getName(), orderCar);
        } catch (MessagingException e) {
            e.printStackTrace();

        }

        return ordersDao.updateStatus(id_order, newStatus);}
    public int updateTotalPrice(UUID id_order, int totalPrice) {return ordersDao.updateTotalPrice(id_order, totalPrice);}
    public int deleteOrder(UUID id_order){return ordersDao.deleteOrder(id_order);}

    String getStatus(String status){
        Map<String,String> map=new HashMap<>();
        map.put("Active","Активне");
        map.put("pending","В обробці");
        map.put("confirmed","Підтверджено");
        map.put("processing","Виконується");
        map.put("shipped","Відправлено");
        map.put("delivered","Доставлено");
        map.put("cancelled","Скасовано");
        return map.get(status);
    }
}
