package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KifiyaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(KifiyaTestApplication.class, args);
    }

    @GetMapping
    public String healthCheck() {
        return "Application is up and running!";
    }
}
