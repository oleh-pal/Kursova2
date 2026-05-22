//package com.example.kursova.api;
//
//import com.example.kursova.services.EmailService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import jakarta.mail.MessagingException;
//
//@RestController
//public class User_Controller {
//
//    private final EmailService emailService;
//
//    public User_Controller(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @GetMapping("/send-confirmation")
//    public String sendConfirmation(@RequestParam String email) throws MessagingException {
//        String confirmationLink = "http://localhost:8080/confirm?token=abc123";
//        emailService.sendConfirmationEmail(email, confirmationLink);
//        return "Лист відправлено!";
//    }
//}


