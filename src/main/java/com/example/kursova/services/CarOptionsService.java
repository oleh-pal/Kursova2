package com.example.kursova.services;

import com.example.kursova.daos.CarOptionsDAO;
import com.example.kursova.daos.CarsDao;
import com.example.kursova.models.CarOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarOptionsService {
    private final CarOptionsDAO optionsDao;
    @Autowired
    public CarOptionsService(CarOptionsDAO optionsDao) {this.optionsDao = optionsDao;}
    public int AddCarOption(CarOptions carOptions) {return optionsDao.insertCarOptions(carOptions);}
    public List<CarOptions> getOptionByIdCar(UUID id){return optionsDao.getOptionByIdCar(id);}
}
