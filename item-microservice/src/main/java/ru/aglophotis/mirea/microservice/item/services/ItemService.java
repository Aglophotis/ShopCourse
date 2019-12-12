package ru.aglophotis.mirea.microservice.item.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.item.entities.Item;
import ru.aglophotis.mirea.microservice.item.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(int id) {
        return itemRepository.findById(id).get();
    }

    public void setAmountById(int amount, int id) {
        Item item = itemRepository.findById(id).get();
        item.setAmount(amount);
        itemRepository.save(item);
    }

    public void insert(Item item) {
        itemRepository.save(item);
    }

    public void delete(int id) {
        itemRepository.deleteById(id);
    }

    public void decrease(int id) {
        Item item = itemRepository.findById(id).get();
        item.setAmount(item.getAmount() - 1);
        itemRepository.save(item);
    }
}
