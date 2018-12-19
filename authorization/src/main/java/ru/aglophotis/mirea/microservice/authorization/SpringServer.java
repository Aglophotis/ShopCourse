package ru.aglophotis.mirea.microservice.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringServer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringServer.class);
        app.run(args);
    }

}