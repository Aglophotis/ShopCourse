package ru.aglophotis.mirea.microservice.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.authorization.entities.PortsConfiguration;
import ru.aglophotis.mirea.microservice.authorization.entities.User;
import ru.aglophotis.mirea.microservice.authorization.utils.TokenGenerator;

@Service
public class AuthorizationService {

    @Autowired
    private PortsConfiguration portsConfiguration;

    public String authorize(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" +
                portsConfiguration.getPort("identity") +
                "/user", request, String.class);
        if (response == null) {
            return "Connection error";
        } else if (response.getBody().equals("Incorrect login") || response.getBody().equals("Incorrect password")) {
            return response.getBody();
        } else {
            return new TokenGenerator().getToken(user.getLogin(), response.getBody());
        }
    }
}
