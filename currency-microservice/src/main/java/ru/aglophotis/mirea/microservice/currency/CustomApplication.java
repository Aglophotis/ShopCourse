package ru.aglophotis.mirea.microservice.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CustomApplication.class);
        app.run(args);
    }

}