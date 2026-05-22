package com.example.kursova.daos;
import com.example.kursova.requests.CarFilterRequest;
import com.example.kursova.models.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarsDao {
    int insertCar(UUID id, Car car);
    default int insertCar(Car car){
        UUID id = UUID.randomUUID();
        return insertCar(id, car);
    };
    Optional<Car> GetCarById(UUID id);
    List<Car> GetAllCars();
    List<Car> filterCars(CarFilterRequest f);
    int DeleteCar(UUID id);
    int updateCarWithImage(UUID id, Car car);
    int updateCarWithoutImage(UUID id, Car car);

}
