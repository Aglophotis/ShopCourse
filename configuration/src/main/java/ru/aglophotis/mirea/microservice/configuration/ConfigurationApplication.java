package ru.aglophotis.mirea.microservice.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConfigurationApplication.class);
        app.run(args);
    }

}