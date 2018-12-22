package ru.aglophotis.mirea.microservice.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.cart.entities.PortsConfiguration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@SpringBootApplication
public class CustomApplication {

    @Autowired
    private PortsConfiguration portsConfiguration;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CustomApplication.class);
        app.run(args);
    }

    @PostConstruct
    private void init() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap<String, String>> response = restTemplate.exchange(
                "http://localhost:8088/config",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HashMap<String, String>>() {});
        portsConfiguration.setMapPorts(response.getBody());
    }
}