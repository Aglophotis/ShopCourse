package ru.aglophotis.mirea.microservice.identity.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.identity.dao.UserDao;
import ru.aglophotis.mirea.microservice.identity.entities.User;

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

    public User getUserByLogin(String username) {
        User user = userDao.getUserByLogin(username);
        user.setPassword(null);
        return user;
    }
}
