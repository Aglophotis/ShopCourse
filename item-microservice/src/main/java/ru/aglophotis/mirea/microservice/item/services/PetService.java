package ru.aglophotis.mirea.microservice.item.services;

import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.item.dao.ItemDao;
import ru.aglophotis.mirea.microservice.item.entities.Item;

import java.util.List;

@Service
public class PetService {

    private ItemDao itemDaoApi;

    public PetService() {
        itemDaoApi = new ItemDao();
    }

    public List<Item> getPets() {
        return itemDaoApi.getPets();
    }

    public Item getPet(int id) {
        return itemDaoApi.getById(id);
    }

}
