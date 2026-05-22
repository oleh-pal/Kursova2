package com.example.kursova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class KursovaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KursovaApplication.class, args);
    }

}
