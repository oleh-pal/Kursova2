package com.example.kursova.services;

import com.example.kursova.requests.CarFilterRequest;
import com.example.kursova.daos.CarsDao;
import com.example.kursova.models.Car;
import com.example.kursova.models.CarOptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {
    private final CarsDao carsDao;
    private final CarOptionsService carOptionsService;

    @Autowired
    public CarService(CarsDao carsDao, CarOptionsService carOptionsService) {
        this.carsDao = carsDao;
        this.carOptionsService = carOptionsService;
    }
    @Transactional
    public void AddCar(
            Car car,
            List<CarOptions> carOptions,
            MultipartFile mainFile,
            MultipartFile[] additionalFiles
    ) throws IOException {

        UUID carId = UUID.randomUUID();

        String uploadsDir = new ClassPathResource("static/uploads/")
                .getFile().getAbsolutePath();

        if (mainFile != null && !mainFile.isEmpty()) {
            String fileName = car.getMarka() + "_main_" +
                    System.currentTimeMillis() + "_" +
                    mainFile.getOriginalFilename();

            Path filePath = Paths.get(uploadsDir, fileName);
            Files.copy(mainFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            car.setImage("http://localhost:8080/uploads/" + fileName);
        }
        if (additionalFiles != null && additionalFiles.length > 0) {
            List<String> photoUrls = new ArrayList<>();

            for (MultipartFile file : additionalFiles) {
                if (file.isEmpty()) continue;

                String fileName = car.getMarka() + "_add_" +
                        System.currentTimeMillis() + "_" +
                        file.getOriginalFilename();

                Path filePath = Paths.get(uploadsDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                photoUrls.add("http://localhost:8080/uploads/" + fileName);
            }
            car.setAdditionalPhotosArray(String.join(";", photoUrls));
        }
        carsDao.insertCar(carId, car);
        for (CarOptions option : carOptions) {
            option.setId_car(carId);
            carOptionsService.AddCarOption(option);
        }
    }

    public List<Car> GetAllCars() {
        return carsDao.GetAllCars();
    }

    ;

    public Optional<Car> GetCarById(UUID id) {
        return carsDao.GetCarById(id);
    }
    public List<Car> filterCars(CarFilterRequest filter) {
        return carsDao.filterCars(filter);
    }

    public int DeleteCar(UUID id) {
        return carsDao.DeleteCar(id);
    }

    ;

    @Transactional
    public int updateCar(UUID id, Car car, MultipartFile mainFile) throws IOException {

        if (mainFile != null && !mainFile.isEmpty()) {

            String uploadsDir = new ClassPathResource("static/uploads/")
                    .getFile().getAbsolutePath();

            String fileName = car.getMarka() + "_main_" +
                    System.currentTimeMillis() + "_" +
                    mainFile.getOriginalFilename();

            Path filePath = Paths.get(uploadsDir, fileName);
            Files.copy(mainFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            car.setImage("http://localhost:8080/uploads/" + fileName);
            return carsDao.updateCarWithImage(id, car);
        }
        return carsDao.updateCarWithoutImage(id, car);
    }
}
