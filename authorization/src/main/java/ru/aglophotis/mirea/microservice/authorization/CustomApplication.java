package ru.aglophotis.mirea.microservice.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.authorization.entities.PortsConfiguration;

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
        HashMap<String, String> portsMap = new HashMap<>();
        portsMap.put("item", "8085");
        portsMap.put("balance", "8095");
        portsMap.put("cart", "8081");
        portsMap.put("currency", "8080");
        portsMap.put("identity", "8083");
        portsMap.put("authorization", "8086");
        portsConfiguration.setMapPorts(portsMap);
    }
}