package ru.aglophotis.mirea.microservice.identity.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.identity.shop.entities.User;
import ru.aglophotis.mirea.microservice.identity.shop.services.UserService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = {"application/json"})
    @ResponseBody
    public String getUsers(@RequestBody User user) {
        if (userService.addUser(user) != -1) {
            return "User successfully added";
        } else {
            return "Error";
        }
    }
}
