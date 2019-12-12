package ru.aglophotis.mirea.microservice.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aglophotis.mirea.microservice.item.entities.Item;

import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByType(String type);
    Item findByTypeAndId(String type, int id);
}
