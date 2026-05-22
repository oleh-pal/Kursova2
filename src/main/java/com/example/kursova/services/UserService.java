package com.example.kursova.services;

import com.example.kursova.daos.UsersDao;
import com.example.kursova.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UsersDao usersDao;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public int AddUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return usersDao.insertUser(user);
    }

    public boolean login(String email, String password) {
        return usersDao.login(email, password);
    }

    public boolean isEmailActivated(String email) {return usersDao.isEmailActivated(email);}

    public Optional<Users> findByEmail(String email) {
        return usersDao.findByEmail(email);
    }

    public String findUserNameById(UUID id) {
        return usersDao.findUserNameById(id);
    }
    public Optional<Users> findById(UUID id) {return usersDao.findById(id);}
    //public List<Users> GetAllUsers(){return usersDao.}
    public int updateUser(Users user) {
        return usersDao.updateUser(user);
    }

    public int UpdateUserPassword(UUID id, String password) {
        return usersDao.updateUserPassword(id, password);
    }
    public int updateUserData(Users user) {
        return usersDao.updateUserData(
                user.getId_users(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
