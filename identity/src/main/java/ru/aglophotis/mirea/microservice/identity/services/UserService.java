package ru.aglophotis.mirea.microservice.identity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.identity.dao.UserDao;
import ru.aglophotis.mirea.microservice.identity.entities.PortsConfiguration;
import ru.aglophotis.mirea.microservice.identity.entities.User;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    private PortsConfiguration portsConfiguration;

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
        return id;
    }

    public String userIsExist(User user) {
        User userDb = userDao.getUserByLogin(user.getLogin());
        if (userDb == null) {
            return "Incorrect login";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(user.getPassword(), userDb.getPassword())) {
            return userDb.getId() + ":" + userDb.getRole();
        } else {
            return "Incorrect password";
        }
    }

    public User getUserByLogin(String username) {
        User user = userDao.getUserByLogin(username);
        user.setPassword(null);
        return user;
    }
}
