package ru.aglophotis.mirea.microservice.identity.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aglophotis.mirea.microservice.identity.shop.entities.User;
import ru.aglophotis.mirea.microservice.identity.shop.services.UserService;

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

    @RequestMapping(value = "/user/token", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String setToken(@RequestBody User user) {
        return userService.setToken(user);
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public String checkToken(@RequestBody String token) {
        return userService.checkToken(token);
    }
}
