package ru.aglophotis.mirea.microservice.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableZuulProxy
public class SpringServer {

    @Bean
    public PreZuulFilter modifyRequestFilter() {
        return new PreZuulFilter();
    }

    @Bean
    public PostZuulFilter modifyResponseFilter() {
        return new PostZuulFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringServer.class, args);
    }

}