package ru.aglophotis.mirea.microservice.item.services;

import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.item.dao.ItemDao;
import ru.aglophotis.mirea.microservice.item.entities.Item;

import java.util.List;

@Service
public class StuffService {

    private ItemDao itemDaoApi;

    public StuffService() {
        itemDaoApi = new ItemDao();
    }

    public List<Item> getStuffs() {
        return itemDaoApi.getStuffs();
    }

    public Item getStuff(int id) {
        return itemDaoApi.getById(id);
    }


}
