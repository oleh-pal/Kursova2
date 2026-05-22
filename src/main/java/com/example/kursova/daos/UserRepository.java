package com.example.kursova.daos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.kursova.models.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserRepository implements UsersDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
    @Override
    public int insertUser(UUID id,Users user) {
        jdbcTemplate.update("INSERT INTO users (id_users,email,role,name,password,phone,emailConfirmed) VALUES (?,?,?,?,?,?,?)",
                id.toString(),user.getEmail(),"USER",user.getName(),user.getPassword(),user.getPhone(),false
                );
        return 1;
    }
    @Override
    public boolean login(String email, String password) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String sql = "SELECT password FROM users WHERE email = ?";
        try {
            String hashedPassword = jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
            if (hashedPassword == null) {
                return false;
            }
            return passwordEncoder.matches(password, hashedPassword);
        } catch (EmptyResultDataAccessException e) {
            // користувача не знайдено
            return false;
        }
    }
    @Override
    public Optional<Users> findByEmail(String email) {
        if(email.isEmpty()){
            return Optional.empty();
        }
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            Users user = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) ->
                    new Users(
                            rs.getString("name"),
                            rs.getString("email"),
                            "",
                            rs.getString("role"),
                            rs.getString("phone"),
                            rs.getBoolean("emailConfirmed"),
                            UUID.fromString(rs.getString("id_users"))
                    )
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }
    @Override
    public String findUserNameById(UUID id){
        String sql = "SELECT name FROM users WHERE id_users = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id.toString()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public Optional<Users> findById(UUID id){
        String sql = "SELECT * FROM users WHERE id_users = ?";
        try {
            Users user = jdbcTemplate.queryForObject(sql, new Object[]{id.toString()}, (rs, rowNum) ->
                    new Users(
                            rs.getString("name"),
                            rs.getString("email"),
                            "",
                            rs.getString("role"),
                            rs.getString("phone"),
                            rs.getBoolean("emailConfirmed"),
                            UUID.fromString(rs.getString("id_users"))
                    )
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public int updateUser(Users user) {
        String sql = "UPDATE users SET emailConfirmed = ? WHERE id_users = ?";
        return jdbcTemplate.update(sql, user.isEmailConfirmed(), user.getId_users().toString());
    }
    @Override
    public boolean isEmailActivated(String email){
        String sql="SELECT emailConfirmed FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, Boolean.class);
    }
    @Override
    public int updateUserData(UUID id, String name, String email, String phone) {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ? WHERE id_users = ?";
        return jdbcTemplate.update(sql, name, email, phone, id.toString());
    }
    @Override
    public int updateUserPassword(UUID id, String password) {
        String sql="UPDATE users SET password=? WHERE id_users=?";
        jdbcTemplate.update(sql,password,id.toString());
        return 1;
    }
}
