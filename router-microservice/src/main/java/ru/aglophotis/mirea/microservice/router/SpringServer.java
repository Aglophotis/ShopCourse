package ru.aglophotis.mirea.microservice.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableZuulProxy
public class SpringServer {

    public static void main(String[] args) {
        SpringApplication.run(SpringServer.class, args);
    }

}