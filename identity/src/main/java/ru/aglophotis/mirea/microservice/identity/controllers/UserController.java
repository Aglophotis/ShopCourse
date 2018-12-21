package ru.aglophotis.mirea.microservice.identity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aglophotis.mirea.microservice.identity.entities.User;
import ru.aglophotis.mirea.microservice.identity.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String userIsExist(@RequestBody User user) {
        return userService.userIsExist(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = {"application/json"})
    @ResponseBody
    public String addUsers(@RequestBody User user) {
        if (userService.addUser(user) != -1) {
            return "User successfully added";
        } else {
            return "A user with this login already exists";
        }
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public User getUser(@RequestBody String username) {
        return userService.getUserByLogin(username);
    }
}
