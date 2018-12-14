package ru.aglophotis.mirea.microservice.item.shop.entities;

import ru.aglophotis.mirea.microservice.item.shop.data.DatabaseContract;

import javax.persistence.*;

@Entity
@Table(name = DatabaseContract.TABLE_NAME)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseContract.COLUMN_ID)
    private int id;

    @Column(name = DatabaseContract.COLUMN_NAME, unique = true)
    private String name;

    @Column(name = DatabaseContract.COLUMN_TYPE)
    private String type;

    @Column(name = DatabaseContract.COLUMN_PRICE)
    private int price;

    @Column(name = DatabaseContract.COLUMN_AMOUNT)
    private int amount;

    public Item(String name, String type, int price, int amount) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.amount = amount;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
