package ru.aglophotis.mirea.microservice.item.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.item.entities.Item;
import ru.aglophotis.mirea.microservice.item.services.ItemService;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Item getItem(@PathVariable("id") int id) {
        return itemService.getItemById(id);
    }

    @RequestMapping(value = "/item/{id}/{amount}", method = RequestMethod.POST)
    @ResponseBody
    public String setAmountById(@PathVariable("id") int id, @PathVariable("amount") int amount) {
        itemService.setAmountById(amount, id);
        return "Successfully update";
    }
}
