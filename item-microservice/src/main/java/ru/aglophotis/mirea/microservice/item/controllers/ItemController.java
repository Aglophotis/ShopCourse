package ru.aglophotis.mirea.microservice.item.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.item.entities.Item;
import ru.aglophotis.mirea.microservice.item.services.ItemService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItems() {
        return itemService.getAllItems().stream().sorted(Comparator.comparingInt(Item::getId)).collect(Collectors.toList());
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

    @PutMapping("/item")
    public ResponseEntity addItem(@RequestBody Item item) {
        itemService.insert(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity deleteItem(@PathVariable int id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/item/{id}")
    public ResponseEntity decreaseItem(@PathVariable int id) {
        itemService.decrease(id);
        return ResponseEntity.ok().build();
    }
}
