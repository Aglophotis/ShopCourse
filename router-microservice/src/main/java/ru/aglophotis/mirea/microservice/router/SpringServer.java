package ru.aglophotis.mirea.microservice.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableZuulProxy
public class SpringServer {

    @Bean
    public CustomerZuulFilter modifyRequestFilter() {
        return new CustomerZuulFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringServer.class, args);
    }

}