package ru.aglophotis.mirea.microservice.currency.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class SpringServer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }

}