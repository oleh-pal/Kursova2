package com.example.kursova.api;

import com.example.kursova.models.CarOptions;
import com.example.kursova.services.CarOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1")
@RestController
public class CarOptionController {
    private final CarOptionsService carOptionsService;
    @Autowired
    public CarOptionController(CarOptionsService carOptionsService) {this.carOptionsService = carOptionsService;}
    @GetMapping("/get-car-options/{id_car}")
    public List<CarOptions> getCarOptions(@PathVariable("id_car") UUID id_car) {return carOptionsService.getOptionByIdCar(id_car);}

}
