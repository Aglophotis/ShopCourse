package ru.aglophotis.mirea.microservice.balance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.balance.entities.UserInfo;
import ru.aglophotis.mirea.microservice.balance.utils.TokenUtils;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(user.getLogin(), "default", authorities);
    }

    private UserInfo getUserByLogin(String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(username);
        ResponseEntity<UserInfo> response
                = restTemplate.postForEntity("http://localhost:8083/getUser", request, UserInfo.class);
        return response.getBody();
    }
}
