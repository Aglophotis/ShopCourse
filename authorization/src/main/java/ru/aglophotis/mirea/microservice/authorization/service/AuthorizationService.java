package ru.aglophotis.mirea.microservice.authorization.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.authorization.entities.User;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthorizationService {

    public String authorize(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8083/user", request, String.class);
        if (response.getBody().equals("Incorrect login")) {
            return "Incorrect login";
        } else if (response.getBody().equals("Incorrect password")) {
            return "Incorrect password";
        } else {
            headers = createHeaders(user.getLogin(), response.getBody());
            String token = headers.get("Authorization").get(0);
            user.setToken(token);
            request = new HttpEntity<>(user, headers);
            response = restTemplate.postForEntity("http://localhost:8083/user/token", request, String.class);
            if (response.getBody().equals("Token update")) {
                return token;
            } else {
                return "Error";
            }
        }
    }

    private HttpHeaders createHeaders(String username, String role) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", createToken(username, role));
        return headers;
    }

    private String createToken(String username, String role) {
        StringBuilder auth = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        auth.append(uuid);
        auth.append(":");
        auth.append(username);
        auth.append(":");
        auth.append(role);
        auth.append(":");
        auth.append(new Date().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        auth.append(":");
        auth.append(calendar.getTime().getTime());
        byte[] encodedAuth = Base64.encodeBase64(
                auth.toString().getBytes(Charset.forName("US-ASCII")));
        return new String(encodedAuth);
    }
}
