package com.example.kursova.daos;

import com.example.kursova.requests.CarFilterRequest;
import com.example.kursova.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CarRepository implements CarsDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CarRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public int insertCar(UUID id, Car car) {
        return jdbcTemplate.update(
                "INSERT INTO cars (" +
                        "id_cars, marka, model, color, year, engine_type, engine_power, doors, price, " +
                        "transmission, image, body_type, description, `condition`,additionalPhotos" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id.toString(),
                car.getMarka(),
                car.getModel(),
                car.getColor(),
                car.getYear(),
                car.getEngine_type(),
                car.getEngine_power(),
                car.getDoors(),
                car.getPrice(),
                car.getTransmission(),
                car.getImage(),
                car.getBody_type(),
                car.getDescription(),
                car.getCondition(),
                car.getAdditionalPhotosArray()
        );
    }


    @Override
    public List<Car> GetAllCars(){
        String sql = "SELECT * FROM cars";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            UUID id = UUID.fromString(resultSet.getString("id_cars"));
            String marka = resultSet.getString("marka");
            int year = resultSet.getInt("year");
            String color = resultSet.getString("color");
            String model = resultSet.getString("model");
            String body_type = resultSet.getString("body_type");
            String engine = resultSet.getString("engine_type");
            String transmission = resultSet.getString("transmission");
            String image = resultSet.getString("image");
            int price = resultSet.getInt("price");
            int doors = resultSet.getInt("doors");
            int engine_power = resultSet.getInt("engine_power");
            String description = resultSet.getString("description");
            String condition = resultSet.getString("condition");
            String additionalPhotosArray = resultSet.getString("additionalPhotos");
            return new Car(marka,model,color,year,engine,engine_power,doors,price,transmission,image,body_type,description,condition,additionalPhotosArray,id);
        });
    }
    @Override
    public Optional<Car> GetCarById(UUID id) {
        String sql = "SELECT * FROM cars WHERE id_cars = ?";

        try {
            Car car = jdbcTemplate.queryForObject(sql, new Object[]{id.toString()}, (rs, rowNum) ->
                    new Car(
                            rs.getString("marka"),
                            rs.getString("model"),
                            rs.getString("color"),
                            rs.getInt("year"),
                            rs.getString("engine_type"),
                            rs.getInt("engine_power"),
                            rs.getInt("doors"),
                            rs.getInt("price"),
                            rs.getString("transmission"),
                            rs.getString("image"),
                            rs.getString("body_type"),
                            rs.getString("description"),
                            rs.getString("condition"),
                            rs.getString("additionalPhotos"),
                            UUID.fromString(rs.getString("id_cars"))
                    )
            );
            return Optional.ofNullable(car);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    @Override
    public List<Car> filterCars(CarFilterRequest f) {

        StringBuilder sql = new StringBuilder("SELECT * FROM cars WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (f.getSearch() != null && !f.getSearch().isEmpty()) {
            sql.append(" AND (marka LIKE ? OR model LIKE ?) ");
            params.add("%" + f.getSearch() + "%");
            params.add("%" + f.getSearch() + "%");
        }

        if (f.getColor() != null) {
            sql.append(" AND color = ? ");
            params.add(f.getColor());
        }

        if (f.getBodyType() != null) {
            sql.append(" AND body_type = ? ");
            params.add(f.getBodyType());
        }

        if (f.getTransmission() != null) {
            sql.append(" AND transmission = ? ");
            params.add(f.getTransmission());
        }

        if (f.getEngineType() != null) {
            sql.append(" AND engine_type = ? ");
            params.add(f.getEngineType());
        }

        if (f.getCondition() != null) {
            sql.append(" AND `condition` = ? ");
            params.add(f.getCondition());
        }

        if (f.getYearFrom() != null) {
            sql.append(" AND year >= ? ");
            params.add(f.getYearFrom());
        }

        if (f.getYearTo() != null) {
            sql.append(" AND year <= ? ");
            params.add(f.getYearTo());
        }

        if (f.getPriceFrom() != null) {
            sql.append(" AND price >= ? ");
            params.add(f.getPriceFrom());
        }

        if (f.getPriceTo() != null) {
            sql.append(" AND price <= ? ");
            params.add(f.getPriceTo());
        }

        if (f.getEnginePowerFrom() != null) {
            sql.append(" AND engine_power >= ? ");
            params.add(f.getEnginePowerFrom());
        }

        if (f.getEnginePowerTo() != null) {
            sql.append(" AND engine_power <= ? ");
            params.add(f.getEnginePowerTo());
        }

        if (f.getDoorFrom() != null) {
            sql.append(" AND doors >= ? ");
            params.add(f.getDoorFrom());
        }

        if (f.getDoorTo() != null) {
            sql.append(" AND doors <= ? ");
            params.add(f.getDoorTo());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) ->
                new Car(
                        rs.getString("marka"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getInt("year"),
                        rs.getString("engine_type"),
                        rs.getInt("engine_power"),
                        rs.getInt("doors"),
                        rs.getInt("price"),
                        rs.getString("transmission"),
                        rs.getString("image"),
                        rs.getString("body_type"),
                        rs.getString("description"),
                        rs.getString("condition"),
                        rs.getString("additionalPhotos"),
                        UUID.fromString(rs.getString("id_cars"))
                )
        );
    }

    @Override
    public int DeleteCar(UUID id){
        String sql = "DELETE FROM cars WHERE id = ?";
        Object[] args = new Object[] {id.toString()};
        int row = jdbcTemplate.update(sql, args);
        return row;
    }
    @Override
    public int updateCarWithoutImage(UUID id, Car car) {
        String sql = """
        UPDATE cars SET 
            marka = ?,
            model = ?,
            color = ?,
            year = ?,
            engine_type = ?,
            engine_power = ?,
            doors = ?,
            price = ?,
            transmission = ?,
            body_type = ?,
            description = ?,
            `condition` = ?,
            additionalPhotos = ?
        WHERE id_cars = ?
    """;

        return jdbcTemplate.update(sql,
                car.getMarka(),
                car.getModel(),
                car.getColor(),
                car.getYear(),
                car.getEngine_type(),
                car.getEngine_power(),
                car.getDoors(),
                car.getPrice(),
                car.getTransmission(),
                car.getBody_type(),
                car.getDescription(),
                car.getCondition(),
                car.getAdditionalPhotosArray(),
                id.toString()
        );
    }
    @Override
    public int updateCarWithImage(UUID id, Car car) {
        String sql = """
        UPDATE cars SET 
            marka = ?,
            model = ?,
            color = ?,
            year = ?,
            engine_type = ?,
            engine_power = ?,
            doors = ?,
            price = ?,
            transmission = ?,
            image = ?,
            body_type = ?,
            description = ?,
            `condition` = ?,
            additionalPhotos = ?
        WHERE id_cars = ?
    """;

        return jdbcTemplate.update(sql,
                car.getMarka(),
                car.getModel(),
                car.getColor(),
                car.getYear(),
                car.getEngine_type(),
                car.getEngine_power(),
                car.getDoors(),
                car.getPrice(),
                car.getTransmission(),
                car.getImage(),
                car.getBody_type(),
                car.getDescription(),
                car.getCondition(),
                car.getAdditionalPhotosArray(),
                id.toString()
        );
    }


}
