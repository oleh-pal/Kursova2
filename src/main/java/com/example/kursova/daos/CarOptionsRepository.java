package com.example.kursova.daos;

import com.example.kursova.models.CarOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public class CarOptionsRepository implements CarOptionsDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CarOptionsRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
    @Override
    public int insertCarOptions(UUID id, CarOptions carOptions){
        return jdbcTemplate.update(
                "INSERT INTO car_options (idcar_options,id_car,option_name,option_price)VALUES (?,?,?,?)",
                id.toString(),
                carOptions.getCar_id().toString(),
                carOptions.getOption_name(),
                carOptions.getOption_price()
                );
    };
    @Override
    public List<CarOptions> getOptionByIdCar(UUID id) {
        String sql = "SELECT * FROM car_options WHERE id_car = ?";
        return jdbcTemplate.query(sql,new Object[]{id.toString()},(resultSet, rowNum) -> {
            UUID id_option=UUID.fromString(resultSet.getString("idcar_options"));
            UUID id_car=UUID.fromString(resultSet.getString("id_car"));
            String option_name=resultSet.getString("option_name");
            int option_price=resultSet.getInt("option_price");
            return new CarOptions(id_option,id_car,option_name,option_price);
        });


    }
}
