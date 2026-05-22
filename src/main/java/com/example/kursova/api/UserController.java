
package com.example.kursova.api;

import com.example.kursova.models.Users;
import com.example.kursova.requests.*;
import com.example.kursova.security.JwtUtil;
import com.example.kursova.services.EmailService;
import com.example.kursova.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, EmailService emailService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        String html;
        try {
            String email = jwtUtil.extractEmail(token);
            Optional<Users> optionalUser = userService.findByEmail(email);

            if (optionalUser.isEmpty()) {
                html = "<h2>Невірний або прострочений токен</h2>";
                return ResponseEntity.badRequest()
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .body(html);
            }

            Users user = optionalUser.get();

            if (!user.isEmailConfirmed()) {
                user.setEmailConfirmed(true);
                userService.updateUser(user);
            }

            html = "<h2>Email підтверджено успішно!</h2>"
                    + "<p>Тепер ви можете <a href='http://127.0.0.1:5500/pages/login.html'>увійти в свій акаунт</a>.</p>";

            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);

        } catch (Exception e) {
            e.printStackTrace();
            html = "<h2>Сталася помилка при підтвердженні email</h2>";
            return ResponseEntity.status(400)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);
        }
    }


    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody Users user) {
        // Збереження користувача
        int result = userService.AddUser(user);
        if (result != 1) {
            return ResponseEntity.status(500).body(Map.of("message", "Помилка створення користувача"));
        }
        Optional<Users> optionalUser = userService.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(500).body(Map.of("message", "Користувача не знайдено після створення"));
        }
        Users newUser = optionalUser.get();
        String token = jwtUtil.generateToken(newUser.getEmail(), newUser.getRole());
        String confirmationLink = "http://localhost:8080/api/v1/confirm?token=" + token;

        try {
            emailService.sendConfirmationEmail(newUser.getEmail(), confirmationLink);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Помилка надсилання підтвердження email"));
        }

        return ResponseEntity.ok(Map.of("message", "Користувача створено. Перевірте email для підтвердження."));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean result = userService.findByEmail(request.getEmail()).isPresent();
        Users user = null;
        if (result) {
            user = (userService.findByEmail(request.getEmail())).get();
        }
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User not found"));
        }
        boolean isActivated=userService.isEmailActivated(request.getEmail());
        if (!isActivated) {
            return ResponseEntity.status(401).body(Map.of("message", "Електронна адреса не підтверджена. Перевірте свою пошту та підтвердьте її."));
        }

        boolean success = userService.login(request.getEmail(), request.getPassword());
        if (!success) {
            return ResponseEntity.status(401).body(Map.of("message", "Невірний логін або пароль"));
        }
        String role = user.getRole();
        String token = jwtUtil.generateToken(request.getEmail(), role);
        return ResponseEntity.ok(Map.of("token", token));
    }


    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest request, Authentication auth) {
        Optional<Users> optionalUser = userService.findByEmail(auth.getName());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "Користувача не знайдено"));
        }

        Users user = optionalUser.get();
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());

        userService.updateUserData(user);

        return ResponseEntity.ok(user);
    }
    @PostMapping("/request-password-change")
    public ResponseEntity<?> requestPasswordChange(@RequestBody ChangePasswordRequest request,
                                                   Authentication auth) {

        String email = auth.getName();
        String newPassword = request.getNewPassword();

        String encodedPassword = passwordEncoder.encode(newPassword);

        String token = jwtUtil.generatePasswordChangeToken(email, encodedPassword);

        String link = "http://localhost:8080/api/v1/confirm-password-change?token=" + token;

        try {
            emailService.sendPasswordChangeEmail(email, link);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(Map.of("message", "Помилка надсилання email"));
        }

        return ResponseEntity.ok(Map.of("message", "Перевірте email для підтвердження зміни пароля."));
    }
    @GetMapping("/confirm-password-change")
    public ResponseEntity<String> confirmPasswordChange(@RequestParam String token) {

        try {
            String email = jwtUtil.extractEmail(token);
            String encodedPassword = jwtUtil.extractNewPassword(token);

            Optional<Users> optionalUser = userService.findByEmail(email);

            if (optionalUser.isEmpty()) {
                return ResponseEntity.badRequest()
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .body("<h2>Невірний або прострочений токен</h2>");
            }

            Users user = optionalUser.get();
            userService.UpdateUserPassword(user.getId_users(), encodedPassword);
            String html = """
            <html><meta charset="UTF-8">
            <h2>Пароль успішно змінено!</h2>
            <p>Тепер ви можете <a href='http://127.0.0.1:5500/pages/login.html'>увійти</a> з новим паролем.</p>
            </html>
        """;

            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body("<h2>Токен недійсний або пошкоджений</h2>");
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        String email = req.getEmail();
        Optional<Users> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            String token = jwtUtil.generateResetPasswordToken(email);
            String resetLink = "http://127.0.0.1:5500/pages/reset_password.html?token=" + token;
            try {
                emailService.sendResetPasswordEmail(email, resetLink);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(Map.of("message", "Якщо email існує — лист з інструкцією надіслано"));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                           @RequestBody ResetPasswordRequest req) {
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(400).body(Map.of("message", "Невірний або прострочений токен"));
        }

        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(Map.of("message", "Неможливо витягти email із токена"));
        }

        Optional<Users> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(400).body(Map.of("message", "Користувача не знайдено"));
        }

        Users user = userOpt.get();
        String newPasswordRaw = req.getNewPassword();
        if (newPasswordRaw == null || newPasswordRaw.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("message", "Пароль має бути щонайменше 6 символів"));
        }

        String encoded = passwordEncoder.encode(newPasswordRaw);
        userService.UpdateUserPassword(user.getId_users(), encoded);

        return ResponseEntity.ok(Map.of("message", "Пароль успішно змінено"));
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        return userService.findByEmail(auth.getName())
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(Map.of("error", "User not found")));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(Authentication auth) {
        Optional<Users> user = userService.findByEmail(auth.getName());

        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/username-by/{user_id}")
    public Map<String, String> getUserName(@PathVariable("user_id") UUID id) {
        String name = userService.findUserNameById(id);
        return Map.of("name", name);
    }
}
