package ru.aglophotis.mirea.microservice.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.authorization.entities.User;
import ru.aglophotis.mirea.microservice.authorization.service.AuthorizationService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthorizationController {
    @Autowired
    private AuthorizationService authService;

    @RequestMapping(value = "/authorization", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String authorize(@RequestBody User user, HttpServletResponse response) {
//        response.addHeader("", "http://localhost:3000");
        return authService.authorize(user);
    }
}
