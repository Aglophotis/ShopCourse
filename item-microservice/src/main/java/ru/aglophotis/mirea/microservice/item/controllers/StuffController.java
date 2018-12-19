package ru.aglophotis.mirea.microservice.item.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aglophotis.mirea.microservice.item.entities.Item;
import ru.aglophotis.mirea.microservice.item.services.StuffService;

import java.util.List;

@Controller
public class StuffController {

    @Autowired
    private StuffService stuffService;

    @RequestMapping(value = "/stuff", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getStuffs(){
        return stuffService.getStuffs();
    }

}
