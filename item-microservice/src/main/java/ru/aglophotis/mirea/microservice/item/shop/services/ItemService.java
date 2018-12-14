package ru.aglophotis.mirea.microservice.item.shop.services;

import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.item.shop.dao.ItemDao;
import ru.aglophotis.mirea.microservice.item.shop.entities.Item;

import java.util.List;

@Service
public class ItemService {

    private ItemDao itemDaoApi;

    public ItemService() {
        itemDaoApi = new ItemDao();
    }

    public List<Item> getAllItems() {
        return itemDaoApi.getAll();
    }

    public Item getItemById(int id) {
        return itemDaoApi.getById(id);
    }

    public void setAmountById(int amount, int itemId) {
        itemDaoApi.setAmountById(amount, itemId);
    }
}
