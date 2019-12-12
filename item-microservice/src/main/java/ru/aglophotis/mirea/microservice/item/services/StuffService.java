package ru.aglophotis.mirea.microservice.item.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.item.entities.Item;
import ru.aglophotis.mirea.microservice.item.repository.ItemRepository;

import java.util.List;

@Service
public class StuffService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getStuffs() {
        return itemRepository.findAllByType("stuff");
    }

    public Item getStuff(int id) {
        return itemRepository.findByTypeAndId("stuff", id);
    }


}
