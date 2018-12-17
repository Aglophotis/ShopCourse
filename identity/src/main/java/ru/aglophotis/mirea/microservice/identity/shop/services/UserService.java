package ru.aglophotis.mirea.microservice.identity.shop.services;

import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.identity.shop.dao.UserDao;
import ru.aglophotis.mirea.microservice.identity.shop.entities.User;

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
        return userDao.insert(user);
    }
}
