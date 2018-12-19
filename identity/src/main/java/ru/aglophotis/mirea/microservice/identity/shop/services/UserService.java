package ru.aglophotis.mirea.microservice.identity.shop.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.identity.shop.dao.UserDao;
import ru.aglophotis.mirea.microservice.identity.shop.entities.User;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public List<User> getUsers(){
        return userDao.getAll();
    }

    public User getUser(int id){
        return userDao.getById(id);
    }

    public int addUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("ROLE_USER");
        Integer id = userDao.insert(user);
        if (id == -1) {
            return id;
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Integer> request = new HttpEntity<>(id);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8095/balance", request, String.class);
        return id;
    }

    public String userIsExist(User user) {
        User userDb = userDao.getUserByLogin(user.getLogin());
        if (userDb == null) {
            return "Incorrect login";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(user.getPassword(), userDb.getPassword())) {
            if (userDb.getRole().equals("ROLE_USER")) {
                return userDb.getId() + ":user";
            } else if (userDb.getRole().equals("ROLE_ADMIN")) {
                return userDb.getId() + ":admin";
            }
        } else {
            return "Incorrect password";
        }
        return null;
    }

    public String setToken(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User userDb = userDao.getUserByLogin(user.getLogin());
        if (userDb != null) {
            if (bCryptPasswordEncoder.matches(user.getPassword(), userDb.getPassword())) {
                if (userDao.updateToken(user) == -1) {
                    return "Error";
                } else {
                    return "Token update";
                }
            }
        }
        return "Error";
    }

    public String checkToken(String token) {
        String encodedToken = new String(Base64.getDecoder().decode(token));
        String[] parseToken = encodedToken.split(":");
        User userDb = userDao.getUserByLogin(parseToken[1]);
        if (userDb == null) {
            return "Incorrect token";
        }
        if (userDb.getToken().equals(token)) {
            return "Token is correct";
        } else {
            return "Incorrect token";
        }
    }
}
