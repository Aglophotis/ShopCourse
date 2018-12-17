package ru.aglophotis.mirea.microservice.item.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aglophotis.mirea.microservice.item.shop.entities.Item;
import ru.aglophotis.mirea.microservice.item.shop.services.PetService;

import java.util.List;

@Controller
public class PetController {

    @Autowired
    private PetService petService;

    @RequestMapping(value = "/pet", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getPets(){
        return petService.getPets();
    }

}
