package com.example.kursova.api;

import com.example.kursova.requests.CarFilterRequest;
import com.example.kursova.models.Car;
import com.example.kursova.models.CarOptions;
import com.example.kursova.services.CarService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequestMapping("api/v1")
@RestController
public class CarController {
    private final CarService carService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarController(CarService carService, ObjectMapper objectMapper) {
        this.carService = carService;
        this.objectMapper = objectMapper;
    }
    @PostMapping(
            value = "/admin/addCars",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> addCar(
            @RequestPart("car") String carJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "photos", required = false) MultipartFile[] photos,
            @RequestPart(value = "options", required = false) String optionsJson
    ) throws IOException {

        Car car = objectMapper.readValue(carJson, Car.class);

        List<CarOptions> options = new ArrayList<>();
        if (optionsJson != null) {
            options = objectMapper.readValue(
                    optionsJson,
                    new TypeReference<>() {
                    }
            );
        }

        carService.AddCar(car, options, file, photos);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get-all-car")
    public List<Car> GetAllCars() {
        return carService.GetAllCars();
    }

    @GetMapping("/get-car-by/{id}")
    public Optional<Car> GetCarById(@PathVariable("id") UUID id) {
        return carService.GetCarById(id);
    }

    @PostMapping("/filter")
    public List<Car> filterCars(@RequestBody CarFilterRequest req) {
        return carService.filterCars(req);
    }
    @DeleteMapping("/delete-car-by-id{id_cars}")
    public int DeleteCar(@PathVariable("id_cars") UUID id_cars) {
        return carService.DeleteCar(id_cars);
    }

    @PutMapping(value ="/update-car/{id_cars}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int updateCar(@PathVariable("id_cars") UUID id_cars,
                         @RequestPart("car") String carJson,
                         @RequestPart(value = "file", required = false) MultipartFile file)
            throws IOException {
        Car car = objectMapper.readValue(carJson, Car.class);
        return carService.updateCar(id_cars, car,file);
    }

}
