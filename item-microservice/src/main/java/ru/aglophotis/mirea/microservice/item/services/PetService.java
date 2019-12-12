package ru.aglophotis.mirea.microservice.item.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.item.entities.Item;
import ru.aglophotis.mirea.microservice.item.repository.ItemRepository;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getPets() {
        return itemRepository.findAllByType("pet");
    }

    public Item getPet(int id) {
        return itemRepository.findByTypeAndId("pet", id);
    }

}
