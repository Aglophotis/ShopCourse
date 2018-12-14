package  ru.aglophotis.mirea.microservice.balance.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class SpringServer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8095"));
        app.run(args);
    }

}