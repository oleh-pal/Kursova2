package com.example.kursova.daos;
import com.example.kursova.models.CarOptions;

import java.util.List;
import java.util.UUID;

public interface CarOptionsDAO {
    int insertCarOptions(UUID id, CarOptions carOptions);
    default int insertCarOptions(CarOptions carOptions){
        UUID id = UUID.randomUUID();
        return insertCarOptions(id, carOptions);
    };
    List<CarOptions> getOptionByIdCar(UUID id);
}


