package com.example.kursova.daos;
import com.example.kursova.models.Car;
import com.example.kursova.models.Users;

import java.util.Optional;
import java.util.UUID;


public interface UsersDao {
    int insertUser(UUID id, Users user);
    default int insertUser(Users user){
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    };
    boolean login(String email, String password);
    boolean isEmailActivated(String email);
    Optional<Users> findByEmail(String email);
    String findUserNameById(UUID id);
    Optional<Users> findById(UUID id);
    int updateUser(Users user);
    int updateUserData(UUID id, String name, String email, String phone);
    int updateUserPassword(UUID id, String password);

}
