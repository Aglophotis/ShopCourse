package ru.aglophotis.mirea.microservice.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.authorization.entities.User;
import ru.aglophotis.mirea.microservice.authorization.service.AuthorizationService;

@RestController
public class AuthorizationController {
    @Autowired
    private AuthorizationService authService;

    @RequestMapping(value = "/authorization", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String authorize(@RequestBody User user) {
        return authService.authorize(user);
    }
}
