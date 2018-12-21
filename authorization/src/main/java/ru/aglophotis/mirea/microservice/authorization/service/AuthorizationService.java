package ru.aglophotis.mirea.microservice.authorization.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.authorization.entities.User;
import ru.aglophotis.mirea.microservice.authorization.utils.TokenGenerator;

@Service
public class AuthorizationService {

    public String authorize(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8083/user", request, String.class);
        if (response == null) {
            return "Connection error";
        } else if (response.getBody().equals("Incorrect login") || response.getBody().equals("Incorrect password")) {
            return response.getBody();
        } else {
            return new TokenGenerator().getToken(user.getLogin(), response.getBody());
        }
    }

//    private HttpHeaders createHeaders(String username, String role) {
//        HttpHeaders headers = new HttpHeaders();
//        TokenGenerator tokenGenerator = new TokenGenerator();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", tokenGenerator.getToken(username, role));
//        return headers;
//    }
}
