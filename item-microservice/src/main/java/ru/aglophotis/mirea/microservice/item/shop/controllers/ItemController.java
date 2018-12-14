package ru.aglophotis.mirea.microservice.item.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aglophotis.mirea.microservice.item.shop.entities.Item;
import ru.aglophotis.mirea.microservice.item.shop.services.ItemService;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItems(){
        return itemService.getAllItems();
    }

    @RequestMapping(value="/item/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Item getItem(@PathVariable("id") int id){
        return itemService.getItemById(id);
    }

    @RequestMapping(value="/item/{id}/{amount}", method=RequestMethod.POST)
    @ResponseBody
    public String setAmountById(@PathVariable("id") int id, @PathVariable("amount") int amount){
        itemService.setAmountById(amount, id);
        return "Successfully update";
    }
}
